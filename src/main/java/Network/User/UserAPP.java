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
import java.sql.SQLOutput;
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
    UserPacket userPacket = null;
    UserMessage userMessage = null;

    public UserAPP(Socket socket) throws IOException {
        this.socket = socket;
        input = new Scanner(System.in);
        dos = new DataOutputStream(socket.getOutputStream());// 출력 전용 >> 직렬화 >> 소켓에 쓰는 것 >> 각 모든 데이터를 Byte
        dis = new DataInputStream(socket.getInputStream());//읽기 전용 >> 소켓의 데이터를 읽는 것 >> Data 어떻게 읽을 지 Byte or int
    }

    public void run() throws IOException {
        boolean isLogin = false;
        boolean isExit = false;

        while ( !isExit ) {
            while ( !isLogin && !isExit ) {
                int command = 0;

                try {
                    System.out.println(UserScreen.Start_SCREEN);
                    command = Integer.parseInt(input.nextLine());
                } catch (InputMismatchException e) {
                    input = new Scanner(System.in);
                    System.out.println(UserScreen.INPUT_ERROR);
                }

                switch (command) {
                    case 1:
                        if (login()) {
                            isLogin = true;
                        }
                        break;
                    case 2:
                        signUp();
                        break;
                    case 3:
                        isExit = true;
                    default:
                        System.out.println(UserScreen.INPUT_ERROR);
                        break;
                }
            }

            while ( isLogin && !isExit ) {
                if ( this.authority == 1 ) {
                    isLogin = clientRun();
                }
                else if ( this.authority == 2 ) {
                    isLogin = ownerRun();
                }
                else if ( this.authority == 3 ) {
                    isLogin = managerRun();
                }
            }

        }
        System.out.println(UserScreen.EXIT);
    }

    private void signUp() throws IOException {
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

        UserDTO dto = makeUserDTO();

        userPacket = new UserPacket(dos, ProtocolType.SIGNUP, ProtocolCode.REGISTER_INFO, authority, ProtocolAnswer.DEFAULT);
        userPacket.sendUserDTO(dto);

        userMessage = new UserMessage(dis);
        userMessage.receiveSignUpResult();
    }

    private UserDTO makeUserDTO() {
        UserDTO temp = new UserDTO();
        String str;
        System.out.println(UserScreen.ENTER_ID);  str = input.nextLine();  temp.setUser_ID(str);
        System.out.println(UserScreen.ENTER_PW);  str = input.nextLine();  temp.setUser_PW(str);
        return temp;
    }

    private boolean login() throws IOException {
        System.out.println(UserScreen.ENTER_ID);
        String id = input.nextLine();
        System.out.println(UserScreen.ENTER_PW);
        String pw = input.nextLine();
        UserDTO dto = new UserDTO(id, pw);

        userPacket = new UserPacket(dos, ProtocolType.LOGIN, ProtocolCode.LOGIN_INFO, ProtocolAuthority.ANONYMITY, ProtocolAnswer.DEFAULT);
        userPacket.sendUserDTO(dto);

        userMessage = new UserMessage(dis);
        this.authority = userMessage.receiveLoginResult();

        if ( this.authority != -1 ) {
            this.user_ID = id;
            return true;
        }
        else {
            return false;
        }
    }

    private boolean clientRun() {
        boolean isRun = true;
        while ( isRun ) {
            int command = 0;

            try {
                System.out.println(UserScreen.CLIENT_SCREEN);
                command = Integer.parseInt(input.nextLine());
            } catch (InputMismatchException e) {
                input = new Scanner(System.in);
                System.out.println(UserScreen.INPUT_ERROR);
            }


        }
        return false;
    }

    private boolean ownerRun() {
        boolean isRun = true;
        while ( isRun ) {
            int command = 0;

            try {
                System.out.println(UserScreen.OWNER_SCREEN);
                command = Integer.parseInt(input.nextLine());
            } catch (InputMismatchException e) {
                input = new Scanner(System.in);
                System.out.println(UserScreen.INPUT_ERROR);
            }

            switch ( command ) {
                case 1:
                    insertStore();
                    break;
                default:
                    System.out.println(UserScreen.INPUT_ERROR);
            }


        }
        return false;
    }

    private void insertStore() {
        System.out.println(UserScreen.ENTER_NAME);
        String storeName = input.nextLine();
        System.out.println(UserScreen.ENTER_ADDRESS);
        String storeAddress = input.next();
    }

    private boolean managerRun() {
        boolean isRun = true;
        while ( isRun ) {
            int command = 0;

            try {
                System.out.println(UserScreen.MANAGER_SCREEN);
                command = Integer.parseInt(input.nextLine());
            } catch (InputMismatchException e) {
                input = new Scanner(System.in);
                System.out.println(UserScreen.INPUT_ERROR);
            }


        }
        return false;
    }

}
