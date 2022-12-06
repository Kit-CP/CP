package Database.persistence.dto;

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
public class MenuDTO implements IDTO{
    private String menu_name;
    private String store_name;
    private String category;
    private int menu_price;
    private int stock;
    private int state;

    public MenuDTO() {
        this.menu_name = "";
        this.store_name = "";
        this.category = "";
        this.menu_price = 0;
        this.stock = 0;
        this.state = 0;
    }

    public MenuDTO(String menu_name, int state) {
        this.menu_name = menu_name;
        store_name = "";
        category = "";
        menu_price= 0;
        stock = 0;
        this.state = state;
    }
    public MenuDTO(String menu_name, String store_name, String category, int menu_price, int stock) {
        this.menu_name = menu_name;
        this.store_name = store_name;
        this.category = category;
        this.menu_price = menu_price;
        this.stock = stock;
        this.state = 0;
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        try {
            dos.writeUTF(menu_name);
            dos.writeUTF(store_name);
            dos.writeUTF(category);
            dos.writeInt(menu_price);
            dos.writeInt(stock);
            dos.writeInt(state);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buf.toByteArray();
    }

    public static MenuDTO readMenuDTO(DataInputStream dis) throws IOException {
        MenuDTO dto = new MenuDTO();
        try {
            dto.setMenu_name(dis.readUTF());
            dto.setStore_name(dis.readUTF());
            dto.setCategory(dis.readUTF());
            dto.setMenu_price(dis.readInt());
            dto.setStock(dis.readInt());
            dto.setState(dis.readInt());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}