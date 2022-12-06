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
public class OrderViewDTO implements IDTO {
    private int order_id;
    private String user_ID;
    private int pricesum;
    private String menu_name;
    private String option_name;
    private int state;

    public OrderViewDTO() {
        order_id = 0;
        user_ID = "";
        pricesum = 0;
        menu_name = "";
        option_name = "";
        state = 0;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeInt(order_id);
            dos.writeUTF(user_ID);
            dos.writeInt(pricesum);
            dos.writeUTF(menu_name);
            dos.writeUTF(option_name);
            dos.writeInt(state);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static OrderViewDTO readOrderViewDTO(DataInputStream dis) {
        OrderViewDTO dto = new OrderViewDTO();
        try {
            dto.setOrder_id(dis.readInt());
            dto.setUser_ID(dis.readUTF());
            dto.setPricesum(dis.readInt());
            dto.setMenu_name(dis.readUTF());
            dto.setOption_name(dis.readUTF());
            dto.setState(dis.readInt());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
