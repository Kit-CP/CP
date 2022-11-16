package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OptionDTO {
    private String option_name;
    private int option_price;
    private String store_name;

    public OptionDTO(String name, int price, String sname) {
        option_name = name;
        option_price = price;
        store_name = sname;
    }
}
