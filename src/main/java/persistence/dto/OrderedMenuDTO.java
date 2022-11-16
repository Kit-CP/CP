package persistence.dto;

public class OrderedMenuDTO {
    private int ordered_menu_id;
    private int order_id;
    private String menu_name;

    public OrderedMenuDTO(int order_id, String menu_name) {
        this.order_id = order_id;
        this.menu_name = menu_name;
    }
}
