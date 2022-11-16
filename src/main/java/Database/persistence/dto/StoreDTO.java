package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreDTO {
    private String store_name;
    private String store_address;
    private String store_phone;
    private int store_score;
    private String information;
    private int isAccept;
    private String user_ID;
    private UserDTO userDTO;
}
