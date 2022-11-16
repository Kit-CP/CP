package Database.view;

import Database.persistence.dto.MenuOptionDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuOptionView {
    public void printAll(List<MenuOptionDTO> dtos) {
        List<String> cate = new ArrayList<>();
        List<String> mename = new ArrayList<>();

        for ( MenuOptionDTO dto : dtos) {
            if ( !cate.contains(dto.getCategory()) ) {
                cate.add(dto.getCategory());
                System.out.println("[" + dto.getCategory() + "]");
            }
            if ( !mename.contains(dto.getMenu_name()) ) {
                mename.add(dto.getMenu_name());
                System.out.print(dto.getMenu_name() + " " + dto.getMenu_price() + "원 ");
            }
            if ( dto.getOption_name() != null ) {
                System.out.print(dto.getOption_name() + " ");
            }
            else {
                System.out.println();
            }
        }
    }
}
