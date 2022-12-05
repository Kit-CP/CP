package Network.User;

import Database.persistence.dto.*;
import Network.Protocol.ProtocolType;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;

public class UserPacket { //메시지를 직렬화
    DataOutputStream dos;
    byte type, code, authority, answer;
    int size = 0;
    byte[] bodyBytes;
    byte[] headerBytes;

    public UserPacket(DataOutputStream dos, byte type, byte code, byte authority, byte answer) {
        this.dos = dos;
        this.type = type;
        this.code = code;
        this.authority = authority;
        this.answer = answer;
    }
    public void sendUserDTO(UserDTO userDTO) {
        try {
            bodyBytes = userDTO.getBytes();
            size = bodyBytes.length;
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);

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
            bodyBytes = storeDTO.getBytes();
            size = bodyBytes.length;
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);

            dos.write(headerBytes);
            dos.write(bodyBytes);

            dos.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void sendNewUserDTO(String userId, UserDTO dto) {
        try {
            bodyBytes = userId.getBytes();
            bodyBytes = ArrayUtils.addAll(bodyBytes, dto.getBytes());
            size = bodyBytes.length;
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);

            dos.write(headerBytes);
            dos.writeUTF(userId);
            dos.write(dto.getBytes());

            dos.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void requestAcceptedStore() {
        try {
            bodyBytes = null;
            size = 0;
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);
            dos.write(headerBytes);
            dos.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
