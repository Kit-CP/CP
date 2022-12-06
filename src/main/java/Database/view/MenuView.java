package Database.view;

import Database.persistence.dto.MenuDTO;
import Util.State;

import java.util.List;

public class MenuView {

    public static void printPendingMenus(List<MenuDTO> dtos) {
        for (MenuDTO dto : dtos) {
            StringBuilder sb = new StringBuilder();
            sb.append(dto.getMenu_name());
            sb.append(", ");
            sb.append(dto.getStore_name());
            sb.append(", ");
            sb.append(dto.getCategory());
            sb.append(", ");
            sb.append(dto.getMenu_price());
            sb.append(", ");
            sb.append(dto.getStore_name());
            sb.append(", ");
            sb.append(State.getState(dto.getState()));
            System.out.println(sb);
        }
    }
}
