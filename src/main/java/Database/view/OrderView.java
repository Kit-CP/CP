package Database.view;

import Database.persistence.dto.OrderViewDTO;
import Database.persistence.dto.MenuSalesDTO;
import Database.persistence.dto.StoreSalesDTO;
import Util.State;

import java.util.ArrayList;
import java.util.List;

public class OrderView {
    public static void userPrint(List<OrderViewDTO> dtos) throws NullPointerException {
        if ( dtos == null )  {
            throw new NullPointerException();
        }
        List<Integer> oidl = new ArrayList<>();
        List<Integer> omidl = new ArrayList<>();

        for ( OrderViewDTO dto : dtos ) {
            if ( !oidl.contains(dto.getOrder_id()) ) {
                oidl.add(dto.getOrder_id());
                System.out.printf("\n주문번호 : %d\t| 상태 : %s\t| 가격 : %s", dto.getOrder_id(), State.getDeliverySate(dto.getState()), dto.getPricesum());
            }
            if ( !omidl.contains(dto.getOrdered_menu_id()) ) {
                omidl.add(dto.getOrdered_menu_id());
                System.out.print("\t| 메뉴 : " + dto.getMenu_name() + " ");
            }
            System.out.print(dto.getOption_name() + " ");
        }
        System.out.println("\n");
    }

    public static void storePrint(List<OrderViewDTO> dtos) throws NullPointerException {
        if ( dtos == null ) {
            throw new NullPointerException();
        }
        List<Integer> oidl = new ArrayList<>();
        List<Integer> omidl = new ArrayList<>();

        for ( OrderViewDTO dto : dtos ) {
            if ( !oidl.contains(dto.getOrder_id()) ) {
                oidl.add(dto.getOrder_id());
                System.out.printf("\n주문번호 : %d\t|유저아이디 : %s\t| 상태 : %s\t| 가격 : %s", dto.getOrder_id(), dto.getUser_ID(), State.getDeliverySate(dto.getState()), dto.getPricesum());
            }
            if ( !omidl.contains(dto.getOrdered_menu_id()) ) {
                omidl.add(dto.getOrdered_menu_id());
                System.out.print("\t| 메뉴 : " + dto.getMenu_name() + " ");
            }
            System.out.print(dto.getOption_name() + " ");
        }
        System.out.println("\n");
    }

    public static void printMenuSales(List<MenuSalesDTO> dtos) {
        int sales = 0;
        if ( dtos == null ) {
            return;
        }
        for (MenuSalesDTO dto : dtos) {
            System.out.printf("메뉴 이름 : %s 주문된 횟수 : %d 총 가격 : %d", dto.getMenu_name(), dto.getCount(), dto.getPriceSum());
            sales += dto.getPriceSum();
        }
        System.out.println("\n총 매출 : " + sales);
    }

    public static void printStoreSales(List<StoreSalesDTO> dtos) {
        if ( dtos == null) {
            return;
        }
        for (StoreSalesDTO dto : dtos) {
            System.out.printf("가게 이름 : %s 총 주문횟수 : %d 총 매출 : %d", dto.getStore_name(), dto.getOrder_count(), dto.getSales());
        }
    }
}
