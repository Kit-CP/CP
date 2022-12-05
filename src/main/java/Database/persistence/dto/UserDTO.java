package Database.persistence.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
public class UserDTO implements IDTO {
    private String user_ID;
    private String user_PW;
    private int authority;
    private String user_address;
    private String user_name;
    private String user_phone;
    private int age;
    private int state;

    public UserDTO() {
        user_ID = user_PW = user_address = user_name = user_phone = "";
        age = 0;
    }
    public UserDTO(String id, String pw, String address, String name, String phone, int age, int state , int authority) {
        user_ID = id;
        user_PW = pw;
        this.authority = authority;
        user_address = address;
        user_name = name;
        user_phone = phone;
        this.age = age;
        this.state = state;
    }

    public UserDTO(String id, String pw) {
        user_ID = id;
        user_PW  = pw;
        age = state = authority = 0;
        user_address = user_name = user_phone = "";
    }

    public UserDTO(String id, int state) {
        user_ID = id;
        user_PW  = "";
        age = authority = 0;
        this.state = state;
        user_address = user_name = user_phone = "";
    }


    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeUTF(user_ID);
            dos.writeUTF(user_PW);
            dos.writeInt(authority);
            dos.writeUTF(user_address);
            dos.writeUTF(user_name);
            dos.writeUTF(user_phone);
            dos.writeInt(age);
            dos.writeInt(state);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static UserDTO readUserDTO(DataInputStream dis) {
        UserDTO dto = new UserDTO();
        try {
            dto.setUser_ID(dis.readUTF());
            dto.setUser_PW(dis.readUTF());
            dto.setAuthority(dis.readInt());
            dto.setUser_address(dis.readUTF());
            dto.setUser_name(dis.readUTF());
            dto.setUser_phone(dis.readUTF());
            dto.setAge(dis.readInt());
            dto.setState(dis.readInt());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
