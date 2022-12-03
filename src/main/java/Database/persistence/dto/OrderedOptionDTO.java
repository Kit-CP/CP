package Database.persistence.dto;

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
public class OrderedOptionDTO implements IDTO {
    private int ordered_option_id;
    private int ordered_menu_id;
    private String option_name;

    public OrderedOptionDTO() {
    }

    public OrderedOptionDTO(int ordered_menu_id, String option_name) {
        this.ordered_menu_id = ordered_menu_id;
        this.option_name = option_name;
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(ordered_option_id);
        dos.writeInt(ordered_menu_id);
        dos.writeUTF(option_name);

        return buf.toByteArray();
    }

    public static OrderedOptionDTO readOrderedOptionDTO(DataInputStream dis) throws IOException {
        OrderedOptionDTO dto = new OrderedOptionDTO();
        dto.setOrdered_option_id(dis.readInt());
        dto.setOrdered_menu_id(dis.readInt());
        dto.setOption_name(dis.readUTF());

        return dto;
    }
}
