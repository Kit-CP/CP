package Network.User;

import Database.persistence.dto.StoreDTO;
import Database.persistence.dto.UserDTO;
import Network.Protocol.ProtocolAnswer;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserMessage {
    private byte answer;
    private byte[] body;
    private int size;
    private DataInputStream dis;


    public UserMessage(DataInputStream dis) {
        try {
            this.answer = dis.readByte();
            this.size = dis.readInt();
            this.dis = dis;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveSignUpResult() {
        if ( answer == ProtocolAnswer.SUCCESS ) {
            System.out.println(UserScreen.SUCCESS_SIGNUP);
        }
        else {
            System.out.println(UserScreen.FAIL_SIGNUP);
        }
    }

    public UserDTO receiveLoginResult() {
        if ( answer == ProtocolAnswer.SUCCESS ) {
            System.out.println(UserScreen.SUCCESS_LOGIN);
            return UserDTO.readUserDTO(dis);
        }
        else {
            System.out.println(UserScreen.FAIL_LOGIN);
            return null;
        }
    }

    public void receiveInsertStoreResult() {
        if ( answer == ProtocolAnswer.SUCCESS ) {
            System.out.println(UserScreen.SUCCESS_REGISTER);
        }
        else {
            System.out.println(UserScreen.FAIL_REGISTER);
        }
    }

    public void receiveInsertResult() {
        if ( answer == ProtocolAnswer.SUCCESS ) {
            System.out.println("등록 성공!");
        }
        else {
            System.out.println("등록 실패!");
        }
    }

    public boolean receiveUpdateInforResult() {
        if ( answer == ProtocolAnswer.SUCCESS ) {
            System.out.println("정보 수정 성공!");
            return true;
        }
        else {
            System.out.println("정보 수정 실패!");
            return false;
        }
    }

    public List<StoreDTO> receiveStoreList() {
        List<StoreDTO> list = new ArrayList<>();
        if ( answer == ProtocolAnswer.SUCCESS ) {
            try {
                int size = dis.readInt();
                for (int i = 0; i < size; i++) {
                    list.add(StoreDTO.readStoreDTO(dis));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("매장이 없습니다.\n");
        }
        return list;
    }

    public void receiveInsertOptionResult() {
        if ( answer == ProtocolAnswer.SUCCESS ) {
            System.out.println(UserScreen.SUCCESS_REGISTER);
        }
        else {
            System.out.println(UserScreen.FAIL_REGISTER);
        }
    }

    public List<UserDTO> receiveUserDTOList() {
        List<UserDTO> list = new ArrayList<>();
        if ( answer == ProtocolAnswer.SUCCESS ) {
            try {
                int size = dis.readInt();
                for ( int i = 0; i < size; i++ ) {
                    list.add(UserDTO.readUserDTO(dis));
                }
                return list;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("보류중인 점주가 없습니다.");
        }
        return null;
    }




}
