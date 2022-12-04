package Network.User;

import Database.persistence.dto.UserDTO;
import Network.Protocol.ProtocolAnswer;
import Network.Protocol.ProtocolAuthority;
import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolType;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

@Getter
@Setter
public class UserAPP {
    Socket socket;
    Scanner input;
    DataInputStream dis;
    DataOutputStream dos;
    String user_ID;
    int authority;

    public UserAPP(Socket socket) throws IOException {
        this.socket = socket;
        input = new Scanner(System.in);
        dos = new DataOutputStream(socket.getOutputStream());// 출력 전용 >> 직렬화 >> 소켓에 쓰는 것 >> 각 모든 데이터를 Byte
        dis = new DataInputStream(socket.getInputStream());//읽기 전용 >> 소켓의 데이터를 읽는 것 >> Data 어떻게 읽을 지 Byte or int
    }

    public void run() throws IOException {
        boolean isLogin = false;

        while ( !isLogin ) {
            int command = 0;

            try {
                System.out.println(UserScreen.START_MENU);
                command = Integer.parseInt(input.nextLine());
            }
            catch (InputMismatchException e) {
                input = new Scanner(System.in);
                System.out.println(UserScreen.INPUT_ERROR);
            }

            switch (command) {
                case 1:
                    //로그인
                    isLogin = true;
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    //APPExit();
                default:
                    System.out.println(UserScreen.INPUT_ERROR);
                    break;
            }
        }
    }

    public void signUp() throws IOException {
        System.out.println(UserScreen.SELECT_AUTHORITY);
        int newAuthority = Integer.parseInt(input.nextLine());
        byte authority = 0;
        if ( newAuthority == 1 ) {
            authority = ProtocolAuthority.CLIENT;
        }
        else if ( newAuthority == 2 ) {
            authority = ProtocolAuthority.OWNER;
        }
        else {
            System.out.println(UserScreen.INPUT_ERROR);
            return;
        }
        System.out.println(UserScreen.SIGNUP);
        String str = input.nextLine();

        String[] strs = str.split(" ");
        UserDTO dto = new UserDTO(strs[0], strs[1], strs[2], strs[3], strs[4], Integer.parseInt(strs[5]), 0, newAuthority);

        UserPacket userPacket = new UserPacket(ProtocolType.SINE_UP, ProtocolCode.REGISTER_INFO,authority, ProtocolAnswer.DEFAULT);
        userPacket.sendSignUpInfo(dos, dto);

        UserMessage userMessage = new UserMessage();
        //body
    }

    public void login() throws IOException {
        System.out.println(UserScreen.ENTER_ID);
        String id = input.nextLine();
        System.out.println(UserScreen.ENTER_PW);
        String pw = input.nextLine();
        UserDTO dto = new UserDTO(id, pw);

    }


}
