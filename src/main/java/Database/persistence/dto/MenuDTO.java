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
public class MenuDTO implements IDTO{
    private String menu_name;
    private String store_name;
    private String category;
    private int menu_price;
    private int stock;
    private int state;

    public MenuDTO() {
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
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(menu_name);
        dos.writeUTF(store_name);
        dos.writeUTF(category);
        dos.writeInt(menu_price);
        dos.writeInt(stock);
        dos.writeInt(state);

        return buf.toByteArray();
    }

    public static MenuDTO readMenuDTO(DataInputStream dis) throws IOException {
        MenuDTO dto = new MenuDTO();
        dto.setMenu_name(dis.readUTF());
        dto.setStore_name(dis.readUTF());
        dto.setCategory(dis.readUTF());
        dto.setMenu_price(dis.readInt());
        dto.setStock(dis.readInt());
        dto.setState(dis.readInt());

        return dto;
    }
}
