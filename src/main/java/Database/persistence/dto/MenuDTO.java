package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuDTO {
    private String menu_name;
    private String store_name;
    private String category;
    private int menu_price;
    private int discount;
    private int stock;
    private int orderCount;

    public MenuDTO(String menu_name, String store_name, String category, int menu_price, int discount, int stock, int orderCount) {
        this.menu_name = menu_name;
        this.store_name = store_name;
        this.category = category;
        this.menu_price = menu_price;
        this.discount = discount;
        this.stock = stock;
        this.orderCount = orderCount;
    }
}
