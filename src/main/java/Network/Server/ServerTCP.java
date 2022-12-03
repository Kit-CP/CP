package Network.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) {
        ServerSocket ss = null;
        Socket socket = null;
        DataInputStream dis;
        DataOutputStream dos;

        try {
            while (true) {
                ss = new ServerSocket(7777);
                System.out.println("create Network.Server. Waiting Connection...\n\n");

                socket = ss.accept();

                System.out.println("User Connected!!\n");

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                //헤더 정보 읽어오기.
                byte type = dis.readByte();
                byte code = dis.readByte();
                byte authority = dis.readByte();
                byte answer = dis.readByte();
                int size = dis.readInt();

                byte[] body = null;
                DataInputStream bodyInfo;
                ServerController ctrl = new ServerController();
                if (size > 0) { //body 읽어오기
                    body = new byte[size];
                    dis.read(body);

                    ctrl.run(type, authority, code, answer, body);

                } else { //body가 없는 경우. >> 헤더 정보만 있을 경우.
                    ctrl.run(type, authority, code, answer, null);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }finally {//무조건 적으로 마지막 수행.
            if (socket != null) {
                try {
                    socket.close(); // 나중에 생각하기.
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
