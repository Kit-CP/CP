package Database.persistence.dto;

import lombok.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NewOrderDTO implements IDTO {
    int order_id;
    String user_ID;
    String store_name;
    String menus_options;

    public NewOrderDTO() {
        this.order_id = 0;
        this.user_ID = "";
        this.store_name = "";
        this.menus_options = "";
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeInt(order_id);
            dos.writeUTF(user_ID);
            dos.writeUTF(store_name);
            dos.writeUTF(menus_options);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static NewOrderDTO readNewOrderDTO(DataInputStream dis) {
        NewOrderDTO dto = new NewOrderDTO();
        try {
            dto.setOrder_id(dis.readInt());
            dto.setUser_ID(dis.readUTF());
            dto.setStore_name(dis.readUTF());
            dto.setMenus_options(dis.readUTF());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
