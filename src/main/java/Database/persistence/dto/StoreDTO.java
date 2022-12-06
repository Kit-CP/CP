package Database.persistence.dto;

import lombok.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class StoreDTO implements IDTO {
    private String store_name;
    private String store_address;
    private String store_phone;
    private int store_score;
    private String information;
    private int isAccept;
    private String user_ID;

    public StoreDTO() {
        this.store_name = "";
        this.store_address = "";
        this.store_phone = "";
        this.store_score = 0;
        this.information = "";
        this.isAccept = 0;
        this.user_ID = "";
    }

    public StoreDTO(String store_name, int state) {
        this.store_name = store_name;
        this.isAccept = state;
        store_address = store_phone = information = user_ID = "";
        store_score = 0;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeUTF(store_name);
            dos.writeUTF(store_address);
            dos.writeUTF(store_phone);
            dos.writeInt(store_score);
            dos.writeUTF(information);
            dos.writeInt(isAccept);
            dos.writeUTF(user_ID);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static StoreDTO readStoreDTO(DataInputStream dis) {
        StoreDTO dto = new StoreDTO();
        try {
            dto.setStore_name(dis.readUTF());
            dto.setStore_address(dis.readUTF());
            dto.setStore_phone(dis.readUTF());
            dto.setStore_score(dis.readInt());
            dto.setInformation(dis.readUTF());
            dto.setIsAccept(dis.readInt());
            dto.setUser_ID(dis.readUTF());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
