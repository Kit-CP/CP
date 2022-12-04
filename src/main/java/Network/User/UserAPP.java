package Network.User;

import Database.persistence.dto.StoreDTO;
import Database.persistence.dto.UserDTO;
import Database.service.UserService;
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

        UserDTO dto = makeUserDTO(newAuthority);

        userPacket = new UserPacket(dos, ProtocolType.SIGNUP, ProtocolCode.REGISTER_INFO, authority, ProtocolAnswer.DEFAULT);
        userPacket.sendUserDTO(dto);

        userMessage = new UserMessage(dis);
        userMessage.receiveSignUpResult();
    }

    private UserDTO makeUserDTO(int authority) {
        UserDTO temp = new UserDTO();
        String str;
        System.out.println(UserScreen.ENTER_ID);  str = input.nextLine();  temp.setUser_ID(str);
        System.out.println(UserScreen.ENTER_PW);  str = input.nextLine();  temp.setUser_PW(str);
        System.out.println(UserScreen.ENTER_ADDRESS);  str = input.nextLine();  temp.setUser_address(str);
        System.out.println(UserScreen.ENTER_NAME);  str = input.nextLine();  temp.setUser_name(str);
        System.out.println(UserScreen.ENTER_PHONE);  str = input.nextLine();  temp.setUser_phone(str);
        System.out.println(UserScreen.ENTER_AGE);  str = input.nextLine();  temp.setAge(Integer.parseInt(str));
        temp.setAuthority(authority);
        temp.setState(0);
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

    private boolean ownerRun() throws IOException {
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

    private void insertStore() throws IOException {
        StoreDTO dto = makeStoreDTO();

        userPacket = new UserPacket(dos, ProtocolType.REGISTER, ProtocolCode.STORE_INSERT, ProtocolAuthority.OWNER, ProtocolAnswer.DEFAULT);
        userPacket.sendStoreDTO(dto);

        userMessage = new UserMessage(dis);
        userMessage.receiveInsertStoreResult();
    }

    private StoreDTO makeStoreDTO() {
        StoreDTO temp = new StoreDTO();
        String str;
        System.out.println(UserScreen.ENTER_NAME);  str = input.nextLine();  temp.setStore_name(str);
        System.out.println(UserScreen.ENTER_ADDRESS);  str = input.nextLine();  temp.setStore_address(str);
        System.out.println(UserScreen.ENTER_PHONE);  str = input.nextLine();  temp.setStore_phone(str);
        System.out.println(UserScreen.ENTER_INFORMATION); str = input.nextLine();  temp.setInformation(str);
        temp.setUser_ID(this.user_ID);
        temp.setIsAccept(0);
        return temp;
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
