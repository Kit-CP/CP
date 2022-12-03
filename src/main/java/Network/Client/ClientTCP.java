package Network.Client;

import Network.Server.Protocol;

import java.io.*;
import java.net.*;

public class ClientTCP {
    public static void main(String args[]) {
        Socket cliSocket = null;
        Protocol protocol = new Protocol();
        InputStream is;
        OutputStream os;
        DataInputStream dis;
        DataOutputStream dos;
        BufferedReader br;
        BufferedWriter bw;
        String host = "127.0.0.1";
        SendDTO sendInfo = new SendDTO();
        boolean isSUCCESS = false;
        try {
            while (true) {
                cliSocket = new Socket(host, 7777);
                System.out.println("******** 안녕하세요 반갑습니다. ********");
                System.out.println("[1] 회원가입    [2] 로그인 ");
                br = new BufferedReader(new InputStreamReader(System.in));
                int number = Integer.parseInt(br.readLine());
                dos = new DataOutputStream(cliSocket.getOutputStream());
                byte type = 0, code = 0, authority = 0, answer =0;
                int size = 0;

                while (number < 3  && !isSUCCESS) { //로그인, 회원가입.
                    sendInfo.start(number, dos); //정보 보냄.
                    if(number > 0) {
                        dis = new DataInputStream(cliSocket.getInputStream());
                        type = dis.readByte();
                        code = dis.readByte();
                        authority = dis.readByte();
                        answer = dis.readByte();
                        size = dis.readInt();
                    }

                    byte[] body = new byte[size];
                    DataInputStream bodyInfo;
                    if(size > 0) {
                        bodyInfo = new DataInputStream(new ByteArrayInputStream(body));
                        //객체 역직렬화클래스명 변수 명 = new 역직렬화(bodyInfo);
                        //역직렬화 한 것의 Data 출력.
                    }
                    if(answer != protocol.ERROR) {
                        isSUCCESS = true;
                    }else {
                        System.out.println("[1] 회원가입    [2] 로그인 ");
                        number = Integer.parseInt(br.readLine());
                    }
                }
                System.out.println("------");
                if(authority == protocol.CLIENT) {

                }
                if(authority == protocol.OWNER) {

                }
                if(authority == protocol.MANAGER) {

                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Network.Server not found");
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                cliSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
