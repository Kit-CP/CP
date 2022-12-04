package Network.User;

import Network.Protocol.ProtocolAnswer;

import java.io.DataInputStream;
import java.io.IOException;

public class UserMessage {
    private byte answer;
    private byte[] body;
    private int size;

    public UserMessage(DataInputStream dis) throws IOException {
        this.answer = dis.readByte();
        this.size = dis.readInt();

        if ( size > 0 ) {
            body = new byte[size];
            dis.read(body);
        }
        else {
            body = new byte[0];
        }
    }

    public void receiveSignUpResult() {
        if ( answer == ProtocolAnswer.SUCCESS ) {
            System.out.println(UserScreen.SUCCESS_SIGNUP);
        }
        else if ( answer == ProtocolAnswer.ERROR ) {
            System.out.println(UserScreen.FAIL_SIGNUP);
        }
    }

}
