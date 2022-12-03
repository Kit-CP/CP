package Database.persistence.dto;

import lombok.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderViewDTO implements IDTO {
    private int order_id;
    private String user_ID;
    private int pricesum;
    private String menu_name;
    private String option_name;
    private int state;

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(order_id);
        dos.writeUTF(user_ID);
        dos.writeInt(pricesum);
        dos.writeUTF(menu_name);
        dos.writeUTF(option_name);
        dos.writeInt(state);

        return buf.toByteArray();
    }

    public static OrderViewDTO readOrderViewDTO(DataInputStream dis) throws IOException {
        OrderViewDTO dto = new OrderViewDTO();
        dto.setOrder_id(dis.readInt());
        dto.setUser_ID(dis.readUTF());
        dto.setPricesum(dis.readInt());
        dto.setMenu_name(dis.readUTF());
        dto.setOption_name(dis.readUTF());
        dto.setState(dis.readInt());

        return dto;
    }
}
