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
public class OrderDTO implements IDTO {
    private int order_id;
    private String User_ID;
    private String Store_name;
    private int priceSum;
    private int state;
    private OrderedMenuDTO orderedMenuDTO;
    private OrderedOptionDTO orderedOptionDTO;

    public OrderDTO() {
    }

    public OrderDTO(String user_ID, String store_name) {
        User_ID = user_ID;
        Store_name = store_name;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeInt(order_id);
            dos.writeUTF(User_ID);
            dos.writeUTF(Store_name);
            dos.writeInt(priceSum);
            dos.writeInt(state);
            dos.write(orderedMenuDTO.getBytes());
            dos.write(orderedOptionDTO.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static OrderDTO readOrderDTO(DataInputStream dis) {
        OrderDTO dto = new OrderDTO();
        try {
            dto.setOrder_id(dis.readInt());
            dto.setUser_ID(dis.readUTF());
            dto.setStore_name(dis.readUTF());
            dto.setPriceSum(dis.readInt());
            dto.setState(dis.readInt());
            dto.setOrderedMenuDTO(OrderedMenuDTO.readOrderedMenuDTO(dis));
            dto.setOrderedOptionDTO(OrderedOptionDTO.readOrderedOptionDTO(dis));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
