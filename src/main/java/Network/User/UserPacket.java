package Network.User;

import Database.persistence.dto.*;
import Network.MyListSerializer;
import Network.Protocol.ProtocolType;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.List;

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

    public void sendMenuDTO(MenuDTO dto) {
        try {
            bodyBytes = dto.getBytes();
            size = bodyBytes.length;
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);

            dos.write(headerBytes);
            dos.write(bodyBytes);
            dos.flush();
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    public void sendString(String infor) {
        try {
            size = infor.length();
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);
            dos.write(headerBytes);
            dos.writeUTF(infor);
            dos.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void sendOptionDTOList(List<OptionDTO> list) {
        try {
            MyListSerializer<OptionDTO> temp = new MyListSerializer<>();
            size = list.size();
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);
            dos.write(headerBytes);
            dos.write(temp.listToByte(list));
            dos.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestMyReview(String user_id, String store_name, int crtPage) {
        try {
            size = user_id.getBytes().length;
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);
            dos.write(headerBytes);
            dos.writeUTF(user_id);
            dos.writeUTF(store_name);
            dos.writeInt(crtPage);
            dos.flush();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void request() {
        try {
            int size = 0;
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);
            dos.write(headerBytes);
            dos.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendOrderCancel(OrderDTO orderDTO) { //찬진이가 작성
        try {
            bodyBytes = orderDTO.getBytes();
            size = bodyBytes.length;
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);

            dos.write(headerBytes);
            dos.write(bodyBytes);
            dos.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMenuList(List<MenuDTO> dtos, List<String> strs) {
        try {
            int size = dtos.size();
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);
            dos.write(headerBytes);
            dos.writeInt(size);
            for ( int i = 0; i < size; i++ ) {
                dos.write(dtos.get(i).getBytes());
                dos.writeUTF(strs.get(i));
            }
            dos.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestMyTotalList(String store_name) {
        try {
            headerBytes = ProtocolType.getHeader(type, code, authority, answer, size);
            dos.write(headerBytes);
            dos.writeUTF(store_name);
            dos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
