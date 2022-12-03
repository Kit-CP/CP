package Database.persistence.dto;

import com.mysql.cj.x.protobuf.MysqlxExpr;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
public class OptionDTO implements IDTO {
    private String option_name;
    private int option_price;
    private String store_name;
    private int state;

    public OptionDTO() {
    }

    public OptionDTO(String name, int price, String sname) {
        option_name = name;
        option_price = price;
        store_name = sname;
        state = 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(option_name);
        dos.writeInt(option_price);
        dos.writeUTF(store_name);
        dos.writeInt(state);

        return buf.toByteArray();
    }

    public static OptionDTO readOptionDTO(DataInputStream dis) throws IOException {
        OptionDTO dto = new OptionDTO();
        dto.setOption_name(dis.readUTF());
        dto.setOption_price(dis.readInt());
        dto.setStore_name(dis.readUTF());
        dto.setState(dis.readInt());

        return dto;
    }
}
