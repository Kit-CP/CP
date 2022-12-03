package Network.User;

import Network.Packet;
import Network.Protocol;

import java.io.*;
import java.net.*;

public class UserMain {
    public static void main(String args[]) {
        Socket cliSocket = null;
        InputStream is;
        OutputStream os;
        DataInputStream dis;
        DataOutputStream dos;
        BufferedReader br;
        BufferedWriter bw;
        String host = "192.168.0.26";
        try {
                cliSocket = new Socket(host, 7777);
            while (true) {
                System.out.println("******** 안녕하세요 반갑습니다. ********");
                System.out.println("[1] 회원가입    [2] 로그인 ");
                br = new BufferedReader(new InputStreamReader(System.in));
                int number = Integer.parseInt(br.readLine());

                dos = new DataOutputStream(cliSocket.getOutputStream());// 출력 전용 >> 직렬화 >> 소켓에 쓰는 것 >> 각 모든 데이터를 Byte
                dis = new DataInputStream(cliSocket.getInputStream());//읽기 전용 >> 소켓의 데이터를 읽는 것 >> Data 어떻게 읽을 지 Byte or int
                byte type = 0, code = 0, authority = 0, answer =0;
                int size = 0;

                boolean isSUCCESS = false;
                if(number == 1) {
                    while (!isSUCCESS) {
                        Packet packet = new Packet(Protocol.SINE_UP, Protocol.REGISTER_INFO,Protocol.ANONYMITY,Protocol.DEFAULT);
                        packet.sendSignUpInfo(dos);
                        dis.readByte();
                        isSUCCESS = true;
                        System.out.println("서버로부터 응답수신");
                    }
                }
                boolean isLogin = false;
                while(!isLogin) {
                    //로그인 요청
                }
                if(authority == Protocol.CLIENT) {
                    //로그인 후 작업
                }
                if(authority == Protocol.OWNER) {
                    //로그인 후 작업
                }
                if(authority == Protocol.MANAGER) {
                    //로그인 후 작업
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
