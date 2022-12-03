package Database.persistence.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuSalesDTO {
    String menu_name;
    int count;
    int priceSum;

    public MenuSalesDTO(String menu_name, int count, int priceSum) {
        this.menu_name = menu_name;
        this.count = count;
        this.priceSum = priceSum;
    }
}
