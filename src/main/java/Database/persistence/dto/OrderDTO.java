package Database.persistence.dto;

import lombok.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class OrderDTO implements IDTO {
    private int order_id;
    private String User_ID;
    private String Store_name;
    private int priceSum;
    private int state;

    public OrderDTO() {
        this.order_id = 0;
        User_ID = "";
        Store_name = "";
        this.priceSum = 0;
        this.state = 0;
    }

    public OrderDTO(String user_ID, String store_name) {
        order_id = 0;
        User_ID = user_ID;
        Store_name = store_name;
        priceSum = 0;
        state = 0;
    }

    public OrderDTO(String store_name, int order_id, int state) {
        Store_name = store_name;
        User_ID = "";
        this.order_id = order_id;
        priceSum = 0;
        this.state = state;
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
