package Network.User;

import Database.persistence.dto.*;
import Network.Protocol.ProtocolType;

import java.io.*;

public class UserPacket { //메시지를 직렬화
    byte type, code, authority, answer;
    int size = 0;

    public UserPacket(byte type, byte code, byte authority, byte answer) {
        this.type = type;
        this.code = code;
        this.authority = authority;
        this.answer = answer;
    }
    public void sendSignUpInfo(DataOutputStream dos, UserDTO user) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dataWrite = new DataOutputStream(bao);
        try {
            byte[] bodyBytes = user.getBytes();
            size = bodyBytes.length;
            byte[] headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);

            dataWrite.write(headerBytes);
            dataWrite.write(bodyBytes);

            dos.write(bao.toByteArray());
            dos.flush();

        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
