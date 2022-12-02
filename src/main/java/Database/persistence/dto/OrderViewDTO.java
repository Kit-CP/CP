package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderViewDTO {
    private int order_id;
    private String user_ID;
    private int pricesum;
    private String menu_name;
    private String option_name;
    private int state;
}
