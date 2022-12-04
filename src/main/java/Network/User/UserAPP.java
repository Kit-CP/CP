package Network.User;

import Database.persistence.dto.UserDTO;
import Network.Packet;
import Network.Protocol;
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
                System.out.println(UserMessage.START_MENU);
                command = Integer.parseInt(input.nextLine());
            }
            catch (InputMismatchException e) {
                input = new Scanner(System.in);
                System.out.println(UserMessage.INPUT_ERROR);
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
                    System.out.println(UserMessage.INPUT_ERROR);
                    break;
            }
        }
    }

    public void signUp() throws IOException {
        System.out.println(UserMessage.SELECT_AUTHORITY);
        int newAuthority = Integer.parseInt(input.nextLine());
        byte authority = 0;
        if ( newAuthority == 1 ) {
            authority = Protocol.CLIENT;
        }
        else if ( newAuthority == 2 ) {
            authority = Protocol.OWNER;
        }
        else {
            System.out.println(UserMessage.INPUT_ERROR);
            return;
        }
        System.out.println(UserMessage.SIGNUP);
        String str = input.nextLine();

        String[] strs = str.split(" ");
        UserDTO dto = new UserDTO(strs[0], strs[1], strs[2], strs[3], strs[4], Integer.parseInt(strs[5]), 0, newAuthority);

        Packet packet = new Packet(Protocol.SINE_UP, Protocol.REGISTER_INFO,authority,Protocol.DEFAULT);
        packet.sendSignUpInfo(dos, dto);
        dis.readByte();
    }

    public void login() throws IOException {
        System.out.println(UserMessage.ENTER_ID);
        String id = input.nextLine();
        System.out.println(UserMessage.ENTER_PW);
        String pw = input.nextLine();
        UserDTO dto = new UserDTO(id, pw);

    }


}
