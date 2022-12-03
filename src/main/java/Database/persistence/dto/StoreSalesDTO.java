package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreSalesDTO {
    String store_name;
    int order_count;
    int sales;

    public StoreSalesDTO(String store_name, int order_count, int sales) {
        this.store_name = store_name;
        this.order_count = order_count;
        this.sales = sales;
    }
}
