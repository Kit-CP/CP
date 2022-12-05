package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

@Getter
@Setter
@ToString
public class MenuOptionDTO implements IDTO {
    private String category;
    private String menu_name;
    private String option_name;
    private int menu_price;

    public MenuOptionDTO() {
    }
    public MenuOptionDTO(String category, String menu_name, String option_name, int menu_price) {
        this.category = category;
        this.menu_name = menu_name;
        this.option_name = option_name;
        this.menu_price = menu_price;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeUTF(category);
            dos.writeUTF(menu_name);
            dos.writeUTF(option_name);
            dos.writeInt(menu_price);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static MenuOptionDTO readMenuOptionDTO(DataInputStream dis) {
        MenuOptionDTO dto = new MenuOptionDTO();
        try {
            dto.setCategory(dis.readUTF());
            dto.setMenu_name(dis.readUTF());
            dto.setOption_name(dis.readUTF());
            dto.setMenu_price(dis.readInt());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
