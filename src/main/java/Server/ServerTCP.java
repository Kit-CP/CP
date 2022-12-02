package Server;

import Database.persistence.dto.*;
import com.mysql.cj.protocol.x.ContinuousOutputStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerTCP {
    public static void main(String[] args) {
        //깃테스트
        ServerSocket ss = null;
        Socket socket = null;
        DataInputStream dis;
        DataOutputStream dos;
        ObjectInputStream ois; //구현한 객체 역직렬화 사용해야함.
        ObjectOutputStream oos; // 구현한 객체 직렬화 사용해야함.

        try {
            while (true) {
                ss = new ServerSocket(7777);
                System.out.println("create Server. Waiting Connection...\n\n");

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
                Controller ctrl = new Controller();
                if (size > 0) { //body 읽어오기
                    body = new byte[size];
                    dis.read(body);
                    bodyInfo = new DataInputStream(new ByteArrayInputStream(body));
                    ois = new ObjectInputStream(bodyInfo); // 직접 구현한 직렬화, 역직렬화를 사용.

                    ois.readObject(); // 객체 읽기 >> 각 케이스에 따른 객체 선언 및 연결

                    ctrl.run(type,authority,code,body);

                } else { //body가 없는 경우. >> 헤더 정보만 있을 경우.
                    ctrl.run(type,authority,code,null);
                }
            }
        }catch(IOException e) {
            System.out.println(e);
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }finally {//무조건 적으로 마지막 수행.
            if(socket != null) {
                try{
                    socket.close(); // 나중에 생각하기.
                }catch(IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
