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
        System.out.println("Server Thread " + portNum + " running.");
        while(true) {
            try {
                ServerMessage serverMessage = new ServerMessage(dis);



                serverMessage.run(dos);
                stop();

            }catch(IOException e) {
                System.out.println(portNum + " ERROR reading: " + e.getMessage());
                try {
                    this.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    public void open() throws IOException {
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
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
    }
}
