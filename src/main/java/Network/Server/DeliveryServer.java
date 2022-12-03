package Network.Server;

import java.io.*;
import java.net.*;
public class DeliveryServer implements Runnable {
    private ServerSocket server = null;
    private Thread thread   = null;

    public DeliveryServer(String host, int port) {
        try {
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
                server.accept();
                System.out.println("Connection");
                
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
        }
    }
    public void stop() {
        if(thread != null) {
            thread.stop();
            thread = null;
        }
    }
}
