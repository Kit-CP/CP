package Database.view;

import Database.persistence.dto.OptionDTO;

import java.util.List;
public class OptionView {
    public static void printNamePrice(List<OptionDTO> dtos) {
        if ( dtos == null ) {
            return;
        }
        for ( OptionDTO dto : dtos ) {
            System.out.printf("옵션이름 : %s, 옵션가격 : %d\n", dto.getOption_name(), dto.getOption_price());
        }
    }
}
