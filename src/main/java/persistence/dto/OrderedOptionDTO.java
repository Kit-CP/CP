package persistence.dto;

public class OrderedOptionDTO {
    private int ordered_option_id;
    private int ordered_menu_id;
    private String option_name;

    public OrderedOptionDTO(int ordered_menu_id, String option_name) {
        this.ordered_menu_id = ordered_menu_id;
        this.option_name = option_name;
    }
}
