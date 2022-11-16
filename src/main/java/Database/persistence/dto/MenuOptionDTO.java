package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuOptionDTO {
    private String category;
    private String menu_name;
    private String option_name;
    private int menu_price;
}
