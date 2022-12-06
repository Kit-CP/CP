package Database.view;

import Database.persistence.dto.MenuOptionDTO;

import java.util.ArrayList;
import java.util.List;

public class MenuOptionView {
    public static void printAll(List<MenuOptionDTO> dtos) {
        if ( dtos == null || dtos.size() == 0 ) {
            return;
        }
        List<String> cate = new ArrayList<>();
        List<String> mename = new ArrayList<>();
        String temp = dtos.get(0).getCategory();
        cate.add(temp);
        System.out.println("[" + temp + "]");
        for ( MenuOptionDTO dto : dtos) {
            if ( !cate.contains(dto.getCategory()) ) {
                cate.add(dto.getCategory());
                System.out.println("\n\n[" + dto.getCategory() + "]");
            }
            if ( !mename.contains(dto.getMenu_name()) ) {
                mename.add(dto.getMenu_name());
                System.out.print("\n" + dto.getMenu_name() + " " + dto.getMenu_price() + "원 ");
            }
            if ( dto.getOption_name() != null ) {
                System.out.print(dto.getOption_name() + " ");
            }
        }
        System.out.println();
    }
}
