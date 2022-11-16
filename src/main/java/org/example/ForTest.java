package org.example;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.*;
import Database.persistence.dto.*;
import Database.persistence.dto.OrderedOptionDTO;
import Database.view.MenuOptionView;
import Database.view.StoreView;


import java.util.ArrayList;
import java.util.List;

public class ForTest {
/*
    INSERT INTO `delivery`.`user` (`user_ID`, `user_P/W`, `authority`, `user_address`, `user_name`, `user_phone`, `age`) VALUES ('honsot', 'honsotKIT123', '1', '더미', '김선명', '010-1234-5678', '1');
    INSERT INTO `delivery`.`user` (`user_ID`, `user_P/W`, `authority`, `user_address`, `user_name`, `user_phone`, `age`) VALUES ('moms', 'momsKIT123', '1', '더미', '김성렬', '010-1234-5678', '1');
    INSERT INTO `delivery`.`store` (`store_name`, `store_address`, `store_phone`, `store_score`, `sale`, `information`, `isAccept`, `user_ID`) VALUES ('맘스터치', '경북 구미시 대학로 52', '054-476-9958', '0', '0', '엄마의 마음으로 만듭니다', '1', 'moms');
*/
    public static void test1() {
        StoreDTO sdto = new StoreDTO();
        sdto.setStore_name("한솥도시락 금오공대점");
        sdto.setInformation("맛과 정성을 담았습니다");
        sdto.setStore_phone("054-472-0615");
        sdto.setUser_ID("honsot");
        sdto.setStore_address("경북 구미시 대학로 39");

        StoreDAO storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        storeDAO.insertStore(sdto);
        storeDAO.acceptStore("한솥도시락 금오공대점");
    }

    public static void test2() {
        StoreDAO storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        StoreView storeView = new StoreView();
        List<StoreDTO> all = storeDAO.showAcceptedStore();
        storeView.printAll(all);
    }

    public static void test3() {
        List<OptionDTO> dtos = new ArrayList<>();
        OptionDTO dto1 = new OptionDTO("한솥밥 곱빼기", 400, "한솥도시락 금오공대점");
        OptionDTO dto2 = new OptionDTO("현미밥 교체", 1000, "한솥도시락 금오공대점");
        OptionDTO dto3 = new OptionDTO("계란후라이", 1000, "한솥도시락 금오공대점");
        OptionDTO dto4 = new OptionDTO("청양고추", 300, "한솥도시락 금오공대점");
        dtos.add(dto1);
        dtos.add(dto2);
        dtos.add(dto3);
        dtos.add(dto4);

        OptionDAO optionDAO = new OptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        optionDAO.insertOptionAll(dtos);
    }

    public static void test4() { //더미데이터로 수정해야함
        List<MenuDTO> dtos = new ArrayList<>();
        MenuDTO dto1 = new MenuDTO("싸이버거", "맘스터치", "햄버거", 6000, 0, 10, 0);
        MenuDTO dto2 = new MenuDTO("새우버거", "맘스터치", "햄버거", 5500, 0, 10, 0);
        dtos.add(dto1);
        dtos.add(dto2);

        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuDAO.insertMenuAll(dtos);

        List<MenuHasOptionDTO> dtos2 = new ArrayList<>();
        MenuHasOptionDTO dto3 = new MenuHasOptionDTO("옵션1", "싸이버거");
        MenuHasOptionDTO dto4 = new MenuHasOptionDTO("옵션2", "싸이버거");
        dtos2.add(dto3);
        dtos2.add(dto4);

        MenuHasOptionDAO menuHasOptionDAO = new MenuHasOptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuHasOptionDAO.insertMenuOption(dtos2);
    }

    public static void test5() {
        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        MenuOptionView view = new MenuOptionView();
        view.printAll(menuDAO.showMenu());
    }

    public static void test6() {
        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuDAO.updateMenu("새우버거", "new새우버거", 5050);
    }

    public static void test7() {
        String userID = "test123";
        String storeName = "맘스터치";
        String menuName = "싸이버거";
        String optionName = "옵션2";

        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        int stock = menuDAO.getStock(menuName);
        if ( stock < 1 ) {
            System.out.println("재고 부족, 주문 불가");
            return;
        }
        else {
            System.out.println("주문 완료");
            menuDAO.updateStock(menuName,stock - 1);
        }

        OrderDTO dto1 = new OrderDTO(userID, storeName);
        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderDAO.makeOrder(dto1);
        int orderId = dto1.getOrder_id();

        OrderedMenuDTO dto2 = new OrderedMenuDTO(orderId, menuName);
        OrderedMenuDAO orderedMenuDAO = new OrderedMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderedMenuDAO.orderMenu(dto2);
        int orderedMenuId = dto2.getOrdered_menu_id();

        OrderedOptionDTO dto3 = new OrderedOptionDTO(orderedMenuId, optionName);
        OrderedOptionDAO orderedOptionDAO = new OrderedOptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderedOptionDAO.orderedOption(dto3);
    }

    public static void test9(int orderId, int newState) {
        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderDAO.updateState(orderId, newState);
    }

    public static void test10(int orderId) {
        int state;
        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        state = orderDAO.getOrderState(orderId);
        if (state == 0) {
            orderDAO.cancelOrder(orderId);
            System.out.printf("주문번호 %d가 취소되었습니다.\n", orderId);
        }
        else {
            System.out.print("주문 취소가 불가능합니다\n");
        }
    }

}
