package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderDTO {
    private int order_id;
    private String User_ID;
    private String Store_name;
    private int priceSum;
    private int state;
    private OrderedMenuDTO orderedMenuDTO;
    private OrderedOptionDTO orderedOptionDTO;

    public OrderDTO(String user_ID, String store_name) {
        User_ID = user_ID;
        Store_name = store_name;
    }
}
