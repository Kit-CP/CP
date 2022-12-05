package Network.User;

import Database.persistence.dto.*;
import Network.Protocol.ProtocolType;

import java.io.*;

public class UserPacket { //메시지를 직렬화
    DataOutputStream dos;
    byte type, code, authority, answer;
    int size = 0;
    /*ByteArrayOutputStream bao = new ByteArrayOutputStream();
    DataOutputStream dataWrite = new DataOutputStream(bao);*/

    public UserPacket(DataOutputStream dos, byte type, byte code, byte authority, byte answer) {
        this.dos = dos;
        this.type = type;
        this.code = code;
        this.authority = authority;
        this.answer = answer;
    }
    public void sendUserDTO(UserDTO userDTO) {
        try {
            byte[] bodyBytes = userDTO.getBytes();
            size = bodyBytes.length;
            byte[] headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);

            dos.write(headerBytes);
            dos.write(bodyBytes);

            dos.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void sendStoreDTO(StoreDTO storeDTO) {
        try {
            byte[] bodyBytes = storeDTO.getBytes();
            size = bodyBytes.length;
            byte[] headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);

            dos.write(headerBytes);
            dos.write(bodyBytes);

            dos.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
