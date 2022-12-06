package Database.persistence.dto;

import lombok.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MenuHasOptionDTO implements IDTO {
    private String menu_has_option_id;
    private String option_name;
    private String menu_name;

    public MenuHasOptionDTO() {
        this.menu_has_option_id = "";
        this.option_name = "";
        this.menu_name = "";
    }

    public MenuHasOptionDTO(String option_name, String menu_name) {
        menu_has_option_id = "";
        this.option_name = option_name;
        this.menu_name = menu_name;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        try {
            dos.writeUTF(menu_has_option_id);
            dos.writeUTF(option_name);
            dos.writeUTF(menu_name);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static MenuHasOptionDTO readMenuHasOptionDTO(DataInputStream dis) {
        MenuHasOptionDTO dto = new MenuHasOptionDTO();
        try {
            dto.setMenu_has_option_id(dis.readUTF());
            dto.setOption_name(dis.readUTF());
            dto.setMenu_name(dis.readUTF());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}
