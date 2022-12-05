package Database.view;

import Database.persistence.dto.UserDTO;
import Util.State;

import java.util.List;

public class UserView {
    public static void printAll(List<UserDTO> dtos) {
        for (UserDTO dto : dtos) {
            System.out.printf("ID : %s, 권한 : %s, 주소 : %s, 이름 : %s, 전화번호 : %s, 나이 : %d, 상태 : %s\n", dto.getUser_ID(), State.getAuthority(dto.getAuthority()), dto.getUser_address(), dto.getUser_name(), dto.getUser_phone(), dto.getAge(), State.getState(dto.getState()));
        }
    }
}
