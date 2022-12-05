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
@NoArgsConstructor
public class StoreSalesDTO implements IDTO{
    String store_name;
    int order_count;
    int sales;

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeUTF(store_name);
            dos.writeInt(order_count);
            dos.writeInt(sales);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static StoreSalesDTO readStoreSalesDTO(DataInputStream dis) {
        StoreSalesDTO dto = new StoreSalesDTO();
        try {
            dto.setStore_name(dis.readUTF());
            dto.setOrder_count(dis.readInt());
            dto.setSales(dis.readInt());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
