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
public class StoreDTO implements IDTO {
    private String store_name;
    private String store_address;
    private String store_phone;
    private int store_score;
    private String information;
    private int isAccept;
    private String user_ID;

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(store_name);
        dos.writeUTF(store_address);
        dos.writeUTF(store_phone);
        dos.writeInt(store_score);
        dos.writeUTF(information);
        dos.writeInt(isAccept);
        dos.writeUTF(user_ID);

        return buf.toByteArray();
    }

    public static StoreDTO readStoreDTO(DataInputStream dis) throws IOException {
        StoreDTO dto = new StoreDTO();
        dto.setStore_name(dis.readUTF());
        dto.setStore_address(dis.readUTF());
        dto.setStore_phone(dis.readUTF());
        dto.setStore_score(dis.readInt());
        dto.setInformation(dis.readUTF());
        dto.setIsAccept(dis.readInt());
        dto.setUser_ID(dis.readUTF());

        return dto;
    }
}
