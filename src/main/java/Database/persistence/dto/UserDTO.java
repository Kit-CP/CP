package Database.persistence.dto;

import lombok.Getter;
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
    }


    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(user_ID);
        dos.writeUTF(user_PW);
        dos.writeInt(authority);
        dos.writeUTF(user_address);
        dos.writeUTF(user_name);
        dos.writeUTF(user_phone);
        dos.writeInt(age);
        dos.writeInt(state);

        return buf.toByteArray();
    }

    public static UserDTO readUserDTO(DataInputStream dis) throws IOException {
        UserDTO dto = new UserDTO();

        dto.setUser_ID(dis.readUTF());
        dto.setUser_PW(dis.readUTF());
        dto.setAuthority(dis.readInt());
        dto.setUser_address(dis.readUTF());
        dto.setUser_name(dis.readUTF());
        dto.setUser_phone(dis.readUTF());
        dto.setAge(dis.readInt());
        dto.setState(dis.readInt());

        return dto;
    }
}
