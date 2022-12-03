package Network.Client;

import Network.Server.Protocol;
import java.io.*;

public class ClientController {
    Protocol protocol = new Protocol();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public void start(int number, DataOutputStream dos) throws IOException{
        switch(number) {
            case(1):
                byte type = protocol.SINE_UP;
                byte code = protocol.REGISTER_INFO;
                byte authority = protocol.ANONYMITY;
                byte answer = protocol.DEFAULT;
                String id, pw, name, phone, address;
                System.out.print("새로운 ID를 입력하세요: ");
                id = br.readLine();
                System.out.print("새로운 PW를 입력하세요: ");
                pw = br.readLine();
                System.out.print("사용자의 이름을 입력하세요: ");
                name = br.readLine();
                System.out.print("사용자의 전화 번호를 입력하세요: ");
                phone = br.readLine();
                System.out.print("사용자의 주소를 입력하세요: ");
                address = br.readLine();
                System.out.println("[1] 고객, [2] 점주, [3] 관리자");
                int myType = br.read();
                if(myType == 1) {
                     authority = protocol.CLIENT;
                }
                if(myType == 2) {
                    authority = protocol.OWNER;
                }
                if(myType == 3) {
                    authority = protocol.MANAGER;
                }
                //객체에 각 저장하면서 선언
                int size = 0;
                //int size = 객체 내의 정보들의 바이트 단위들의 합
                protocol.getHeader(type,code,authority,answer,size);//헤더 정보 직렬화

                //dos.write(); << 인자로 보낼 바이트
                //dos.flush();

                break;
            case(2):
                type = protocol.LOGIN;
                code = protocol.LOGIN_INFO;
                authority = protocol.ANONYMITY;
                answer = protocol.DEFAULT;
                System.out.print("ID를 입력하세요: ");
                id = br.readLine();
                System.out.print("PW를 입력하세요: ");
                pw = br.readLine();

                //객체에 각 저장하면서 선언
                size = 0;
                //int size = 객체 내의 정보들의 바이트 단위들의 합
                protocol.getHeader(type,code,authority,answer,size);//헤더 정보 직렬화

                //dos.write(); << 인자로 보낼 바이트
                //dos.flush();
                break;
            default:
                System.out.println("잘못된 값을 입력받았습니다.");
                number = -1;
                break;
        }
    }
    public void orderJob(int number) { //작업 시키기

    }
    public void view() { //인자로 map 받아서 view로 출력.

    }
}
