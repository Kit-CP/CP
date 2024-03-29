package Network.Server;

import java.net.*;
import java.io.*;
public class DeliveryServerThread extends Thread{
    private DeliveryServer server = null;
    private Socket socket = null;
    private int portNum = 0;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;

    public DeliveryServerThread(DeliveryServer server, Socket socket) {
        super();
        this.server = server;
        this.socket = socket;
        portNum = socket.getPort();
    }

    public int getPortNum() {
        return portNum;
    }
    public void run() {
        System.out.println("서버 스레드 " + portNum + " 실행중");
        while( !socket.isClosed() ) {
            try {
                ServerMessage serverMessage = new ServerMessage(dis);
                serverMessage.run(dos);
            }
            catch(IOException e) {
                System.out.println(portNum + "을 종료합니다." + e.getMessage());
                server.remove(portNum);
                stop();
            }

        }
    }

    public void open() throws IOException {
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void close() throws IOException {
        if(socket != null) {
            socket.close();
        }
        if(dis != null) {
            dis.close();
        }
        if(dos != null){
            dos.close();
        }
        this.stop();
    }
}
