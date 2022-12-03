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
public class MenuSalesDTO implements IDTO {
    String menu_name;
    int count;
    int priceSum;

    public MenuSalesDTO() {
    }
    public MenuSalesDTO(String menu_name, int count, int priceSum) {
        this.menu_name = menu_name;
        this.count = count;
        this.priceSum = priceSum;
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(menu_name);
        dos.writeInt(count);
        dos.writeInt(priceSum);

        return buf.toByteArray();
    }

    public static MenuSalesDTO readMenuSalesDTO(DataInputStream dis) throws IOException {
        MenuSalesDTO dto = new MenuSalesDTO();
        dto.setMenu_name(dis.readUTF());
        dto.setCount(dis.readInt());
        dto.setPriceSum(dis.readInt());

        return dto;
    }
}
