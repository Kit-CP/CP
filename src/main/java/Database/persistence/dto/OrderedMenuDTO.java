package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderedMenuDTO {
    private int ordered_menu_id;
    private int order_id;
    private String menu_name;
    private int price;

    public OrderedMenuDTO(int order_id, String menu_name) {
        this.order_id = order_id;
        this.menu_name = menu_name;
    }
}
