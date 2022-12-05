package Network.Server;

import java.io.*;
import java.net.*;
public class DeliveryServer implements Runnable {
    private DeliveryServerThread[] users = new DeliveryServerThread[50];
    private ServerSocket server = null;
    private Thread thread   = null;
    private DataInputStream dis;
    private DataOutputStream dos;
    private int port = 0;
    private int userCount = 0;

    public DeliveryServer(String host, int port) {
        try {
            this.port = port;
            System.out.println("포트 " + port + " 서버를 만듭니다...");
            server = new ServerSocket();
            server.bind(new InetSocketAddress(host, port));
            System.out.println("정상적으로 서버가 생성되었습니다.");
            start();
        } catch (IOException e) {
            System.out.println(port + ": " + e.getMessage());
        }
    }
    @Override
    public void run() {
        while(thread != null) {
            try {
                System.out.println("유저를 기다리는중...");
                /*DeliveryServerThread thread = new DeliveryServerThread(this, server.accept());
                thread.open();
                thread.run();*/
                addThread(server.accept());

            }catch (IOException e) {
                System.out.println("서버 에러: " + e.getMessage());
                stop();
            }
        }
    }

    private int findUser(int portNum) {
        for(int i=0; i < userCount; i++) {
            if (users[i].getPortNum() == portNum) {
                return i;
            }
        }
        return -1;
    }

    public synchronized void remove(int portNum) {
        int pos = findUser(portNum);
        if(pos >= 0) {
            DeliveryServerThread terminate = users[pos];
            System.out.println(portNum + "를 삭제합니다.");
            if (pos < userCount -1) {
                for(int i = pos+1; i<userCount; i++) {
                    users[i-1] = users[i];
                }
                userCount--;
                try{
                    terminate.close();
                } catch (IOException e) {
                    System.out.println("스레드 종료 오류" + e.getMessage());
                }
            }
        }
    }

    public void start() {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
    public void stop() {
        if(thread != null) {
            thread.stop();
            thread = null;
        }
    }

    private void addThread(Socket socket) {
        if(userCount < users.length) {
            System.out.println("유저 연결: " + socket);
            users[userCount] = new DeliveryServerThread(this, socket);
            try {
                users[userCount].open();
                users[userCount].start();
                userCount++;

            }catch(IOException e) {
                System.out.println("에러로 스레드를 정지합니다. : "+ e);
            }
        }
        else {
            System.out.println("유저가 꽉 찼습니다. 현재 " + users.length + "만큼 할당되었습니다.");
        }
    }
}
