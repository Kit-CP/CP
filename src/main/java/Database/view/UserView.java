package Database.view;

import Database.persistence.dto.UserDTO;
import Util.State;

import java.util.List;

public class UserView {
    public static void printAll(List<UserDTO> dtos) {
        System.out.printf("%-7s %-3s %-10s %-4s %-15s %-3s %-3s\n", "유저 ID", "권한", "주소", "이름", "전화번호", "나이", "상태");
        for (UserDTO dto : dtos) {
            System.out.printf("%-7s %-3s %-10s %-4s %-15s %-3s %-3s\n", dto.getUser_ID(), State.getAuthority(dto.getAuthority()), dto.getUser_address(), dto.getUser_name(), dto.getUser_phone(), dto.getAge(), State.getState(dto.getState()));
        }
    }
}
