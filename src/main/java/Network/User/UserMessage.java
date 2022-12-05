package Network.User;

import Database.persistence.dto.UserDTO;
import Network.Protocol.ProtocolAnswer;

import java.io.DataInputStream;
import java.io.IOException;

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

    public UserDTO receiveLoginResult() throws IOException {
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
}
