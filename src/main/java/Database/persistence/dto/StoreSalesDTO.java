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
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(store_name);
        dos.writeInt(order_count);
        dos.writeInt(sales);

        return buf.toByteArray();
    }

    public static StoreSalesDTO readStoreSalesDTO(DataInputStream dis) throws IOException {
        StoreSalesDTO dto = new StoreSalesDTO();
        dto.setStore_name(dis.readUTF());
        dto.setOrder_count(dis.readInt());
        dto.setSales(dis.readInt());

        return dto;
    }
}
