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
            System.out.println("포트번호 " + port + " 으로 생성합니다. 기다려주세요...");
            server = new ServerSocket();
            server.bind(new InetSocketAddress(host, port));
            System.out.println("서버가 구동됩니다...");
            start();
        } catch (IOException e) {
            System.out.println("Can not bind to port " + port + ": " + e.getMessage());
        }
    }
    @Override
    public void run() {
        while(thread != null) {
            try {
                System.out.println("Waiting for a user ...");
                addThread(server.accept());

            }catch (IOException e) {
                System.out.println("서버 오류: " + e.getMessage());
                stop();
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
            System.out.println("Client accepted: " + socket);
            users[userCount] = new DeliveryServerThread(this, socket);
            try {
                users[userCount].open();
                users[userCount].start();
                userCount++;

            }catch(IOException e) {
                System.out.println("Error opening Thread: "+ e);
            }
        }
        else {
            System.out.println("Client refused: maximum " + users.length + " reached.");        }
    }
}
