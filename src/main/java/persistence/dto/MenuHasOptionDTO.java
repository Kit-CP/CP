package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuHasOptionDTO {
    private String menu_has_option_id;
    private String option_name;
    private String menu_name;

    public MenuHasOptionDTO(String option_name, String menu_name) {
        this.option_name = option_name;
        this.menu_name = menu_name;
    }
}
