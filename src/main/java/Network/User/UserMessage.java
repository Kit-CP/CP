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


    public UserMessage(DataInputStream dis) throws IOException {
        this.answer = dis.readByte();
        this.size = dis.readInt();
        this.dis = dis;
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
}
