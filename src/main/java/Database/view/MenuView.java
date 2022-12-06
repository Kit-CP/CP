package Database.view;

import Database.persistence.dto.MenuDTO;
import Util.State;

import java.util.List;

public class MenuView {

    public static void printPendingMenus(List<MenuDTO> dtos) {
        for (MenuDTO dto : dtos) {
            StringBuilder sb = new StringBuilder();
            sb.append("가게 이름: ");
            sb.append(dto.getStore_name());
            sb.append("\t메뉴 이름: ");
            sb.append(dto.getMenu_name());
            sb.append("\t메뉴 가격: ");
            sb.append(dto.getMenu_price());
            sb.append("\t현재 상태: ");
            sb.append(State.getState(dto.getState()));
            System.out.println(sb);
        }
    }
}
