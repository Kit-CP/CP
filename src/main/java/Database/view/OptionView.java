package Database.view;

import Database.persistence.dto.OptionDTO;
import Util.State;

import java.util.List;
public class OptionView {
    public static void printAll(List<OptionDTO> dtos) {
        for ( OptionDTO dto : dtos ) {
            System.out.printf("옵션이름 : %s, 옵션가격 : %d, 가게이름 : %s, 상태 : %s\n", dto.getOption_name(), dto.getOption_price(), dto.getStore_name(), State.getState(dto.getState()));
        }
    }
}
