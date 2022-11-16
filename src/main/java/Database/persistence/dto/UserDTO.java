package Database.persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private String user_ID;
    private String user_PW;
    private int authority;
    private String user_address;
    private String user_name;
    private String user_phone;
    private int age;
}
