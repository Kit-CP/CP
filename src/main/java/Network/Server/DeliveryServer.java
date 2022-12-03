package Network.Server;

import java.io.*;
import java.net.*;
public class DeliveryServer implements Runnable {
    private ServerSocket server = null;
    private Thread thread   = null;
    private DataInputStream dis;
    private DataOutputStream dos;
    private int port = 0;

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
                System.out.println("Waiting for a client ...");
                Socket socket = server.accept();
                System.out.println("Connection");

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                ServerController serverController = new ServerController(dis.readByte(), dis.readByte(), dis.readByte(), dis.readByte());
                int bodySize = dis.readInt();
                if ( bodySize != 0 ) {
                    byte[] bytes = new byte[bodySize];
                    dis.read(bytes);
                    serverController.setSize(bodySize);
                    serverController.setBody(bytes);
                }


                serverController.run(dos); //회원가입 insert
                
                //작업
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
}
