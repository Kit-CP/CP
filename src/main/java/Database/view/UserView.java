package Database.view;

import Database.persistence.dto.UserDTO;

import java.util.List;

public class UserView {
    public static void printAll(List<UserDTO> dtos) {
        System.out.println("유저 정보");
        for (UserDTO dto : dtos) {
            System.out.println(dto.toString());
        }
    }
}
