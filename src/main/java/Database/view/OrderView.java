package Database.view;

import Database.persistence.dto.OrderViewDTO;
import Database.persistence.dto.MenuSalesDTO;
import Database.persistence.dto.StoreSalesDTO;
import Util.State;

import java.util.ArrayList;
import java.util.List;

public class OrderView {
    public static void printAll(List<OrderViewDTO> dtos) {
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

    public void printMenuSales(List<MenuSalesDTO> dtos) {
        int sales = 0;
        if ( dtos == null ) {
            return;
        }
        System.out.println("메뉴이름\t\t\t주문된 횟수\t\t총 가격");
        for (MenuSalesDTO dto : dtos) {
            System.out.print(dto.getMenu_name() + "\t\t");
            System.out.print(dto.getCount() + "\t\t\t\t");
            System.out.println(dto.getPriceSum());
            sales += dto.getPriceSum();
        }
        System.out.println("\n총 매출 : " + sales);
    }

    public void printStoreSales(List<StoreSalesDTO> dtos) {
        if ( dtos == null) {
            return;
        }
        System.out.println("가게이름\t\t\t\t\t총 주문횟수\t\t\t총 매출");
        for (StoreSalesDTO dto : dtos) {
            System.out.print(dto.getStore_name() + "\t\t");
            System.out.print(dto.getOrder_count() + "\t\t\t\t\t");
            System.out.println(dto.getSales());
        }
    }
}
