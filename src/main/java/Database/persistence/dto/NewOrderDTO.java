package Database.persistence.dto;

import lombok.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderDTO implements IDTO {
    int order_id;
    String user_ID;
    String store_name;
    List<String> menus_options;

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(order_id);
        dos.writeUTF(user_ID);
        dos.writeUTF(store_name);

        for (String menu_options : menus_options) {
            dos.writeUTF(menu_options);
        }

        return buf.toByteArray();
    }

    public static NewOrderDTO readNewOrderDTO(DataInputStream dis) throws IOException {
        NewOrderDTO dto = new NewOrderDTO();
        dto.setOrder_id(dis.readInt());
        dto.setUser_ID(dis.readUTF());
        dto.setStore_name(dis.readUTF());
        List<String> menu_options = new ArrayList<>();


        return dto;
    }
}
