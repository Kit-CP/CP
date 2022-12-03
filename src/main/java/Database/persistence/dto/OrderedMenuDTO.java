package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Setter
@Getter
@ToString
public class OrderedMenuDTO implements IDTO {
    private int ordered_menu_id;
    private int order_id;
    private String menu_name;
    private int price;

    public OrderedMenuDTO() {
    }
    public OrderedMenuDTO(int order_id, String menu_name) {
        this.order_id = order_id;
        this.menu_name = menu_name;
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(ordered_menu_id);
        dos.writeInt(order_id);
        dos.writeUTF(menu_name);
        dos.writeInt(price);

        return buf.toByteArray();
    }

    public static OrderedMenuDTO readOrderedMenuDTO(DataInputStream dis) throws IOException {
        OrderedMenuDTO dto = new OrderedMenuDTO();
        dto.setOrdered_menu_id(dis.readInt());
        dto.setOrder_id(dis.readInt());
        dto.setMenu_name(dis.readUTF());
        dto.setPrice(dis.readInt());

        return dto;
    }
}
