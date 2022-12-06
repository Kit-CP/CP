package Database.persistence.dto;

import com.mysql.cj.x.protobuf.MysqlxExpr;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class OptionDTO implements IDTO {
    private String option_name;
    private int option_price;
    private String store_name;
    private int state;

    public OptionDTO() {
        this.option_name = "";
        this.option_price = 0;
        this.store_name = "";
        this.state = 0;
    }

    public OptionDTO(String name, int price, String sname) {
        option_name = name;
        option_price = price;
        store_name = sname;
        state = 0;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeUTF(option_name);
            dos.writeInt(option_price);
            dos.writeUTF(store_name);
            dos.writeInt(state);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static OptionDTO readOptionDTO(DataInputStream dis) {
        OptionDTO dto = new OptionDTO();
        try {
            dto.setOption_name(dis.readUTF());
            dto.setOption_price(dis.readInt());
            dto.setStore_name(dis.readUTF());
            dto.setState(dis.readInt());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
