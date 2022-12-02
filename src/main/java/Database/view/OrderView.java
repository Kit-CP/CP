package Database.view;

import Database.persistence.dto.OrderViewDTO;
import Util.State;

import java.util.ArrayList;
import java.util.List;

public class OrderView {
    public void printAll(List<OrderViewDTO> dtos) {
        if ( dtos == null ) {
            return;
        }
        List<Integer> oidl = new ArrayList<>();
        List<String> ml = new ArrayList<>();
        for ( OrderViewDTO dto : dtos ) {
            if ( !oidl.contains(dto.getOrder_id()) ) {
                oidl.add(dto.getOrder_id());
                System.out.print("\n" + dto.getOrder_id() + " ");
                System.out.print(dto.getUser_ID() + " ");
                System.out.print(State.getDeliverySate(dto.getState()) + " ");
                System.out.print(dto.getPricesum() + " ");
                System.out.print(dto.getMenu_name() + " ");
            }
            if ( dto.getOption_name() != null ) {
                System.out.print(dto.getOption_name() + " ");
            }
        }
    }
}
