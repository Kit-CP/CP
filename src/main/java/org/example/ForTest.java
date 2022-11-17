package org.example;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.*;
import Database.persistence.dto.*;
import Database.persistence.dto.OrderedOptionDTO;
import Database.view.MenuOptionView;
import Database.view.OrderView;
import Database.view.ReviewView;
import Database.view.StoreView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForTest {
    /*
    ALTER TABLE menu AUTO_INCREMENT = 0;
    ALTER TABLE menu_has_option AUTO_INCREMENT = 0;
    ALTER TABLE `option` AUTO_INCREMENT = 0;
    ALTER TABLE `order` AUTO_INCREMENT = 0;
    ALTER TABLE ordered_menu AUTO_INCREMENT = 0;
    ALTER TABLE ordered_option AUTO_INCREMENT = 0;
    ALTER TABLE review AUTO_INCREMENT = 0;
    ALTER TABLE store AUTO_INCREMENT = 0;
    ALTER TABLE user AUTO_INCREMENT = 0;

    INSERT INTO `delivery`.`user` (`user_ID`, `user_P/W`, `authority`, `user_address`, `user_name`, `user_phone`, `age`) VALUES ('honsot', 'honsotKIT123', '1', '더미', '김선명', '010-1234-5678', '1');
    INSERT INTO `delivery`.`user` (`user_ID`, `user_P/W`, `authority`, `user_address`, `user_name`, `user_phone`, `age`) VALUES ('moms', 'momsKIT123', '1', '더미', '김성렬', '010-1234-5678', '1');
    INSERT INTO `delivery`.`user` (`user_ID`, `user_P/W`, `authority`, `user_address`, `user_name`, `user_phone`, `age`) VALUES ('cust1', '1234', '1', '더미', '김대현 ', '010-9567-9976', '23');
    INSERT INTO `delivery`.`user` (`user_ID`, `user_P/W`, `authority`, `user_address`, `user_name`, `user_phone`, `age`) VALUES ('cust2', '1234', '1', '더미', '김민준', '010-4111-4111', '23');

    INSERT INTO delivery.`store` (store_name, store_address, store_phone, store_score, sale, information, isAccept, user_ID) VALUES ('맘스터치 금오공대점', '경북 구미시 대학로 52', '054-476-9958', '0', '0', '엄마의 마음으로 만듭니다', '1', 'moms');
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

    public static void test4() { //더미데이터로 수정해야함 (수정했어 대현아^^)
        List<MenuDTO> dtos = new ArrayList<>();
        MenuDTO dto1 = new MenuDTO("돈까스도련님고기고기", "한솥도시락 금오공대점", "고기고기시리즈", 6000, 0, 10, 0);
        MenuDTO dto2 = new MenuDTO("탕수육도련님고기고기", "한솥도시락 금오공대점", "고기고기시리즈", 5800, 0, 10, 0);
        MenuDTO dto3 = new MenuDTO("새치 고기고기", "한솥도시락 금오공대점", "고기고기시리즈", 6700, 0, 10, 0);
        MenuDTO dto4 = new MenuDTO("돈치 고기고기", "한솥도시락 금오공대점", "고기고기시리즈", 5800, 0, 10, 0);
        MenuDTO dto5 = new MenuDTO("제육 김치찌개 정식", "한솥도시락 금오공대점", "정식시리즈", 8200, 0, 10, 0);
        MenuDTO dto6 = new MenuDTO("제육 김치 부대찌개 정식", "한솥도시락 금오공대점", "정식시리즈", 8500, 0, 10, 0);
        MenuDTO dto7 = new MenuDTO("돈치스팸 김치 부대찌개 정식", "한솥도시락 금오공대점", "정식시리즈", 8500, 0, 1, 0);

        dtos.add(dto1);
        dtos.add(dto2);
        dtos.add(dto3);
        dtos.add(dto4);
        dtos.add(dto5);
        dtos.add(dto6);
        dtos.add(dto7);

        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuDAO.insertMenuAll(dtos);

        List<MenuHasOptionDTO> dtos2 = new ArrayList<>();
        MenuHasOptionDTO odto1 = new MenuHasOptionDTO("한솥밥 곱빼기", "돈까스도련님고기고기");
        MenuHasOptionDTO odto2 = new MenuHasOptionDTO("현미밥 교체", "돈까스도련님고기고기");
        MenuHasOptionDTO odto3 = new MenuHasOptionDTO("계란후라이", "돈까스도련님고기고기");
        MenuHasOptionDTO odto4 = new MenuHasOptionDTO("청양고추", "돈까스도련님고기고기");

        MenuHasOptionDTO odto5 = new MenuHasOptionDTO("한솥밥 곱빼기", "탕수육도련님고기고기");
        MenuHasOptionDTO odto6 = new MenuHasOptionDTO("현미밥 교체", "탕수육도련님고기고기");
        MenuHasOptionDTO odto7 = new MenuHasOptionDTO("계란후라이", "탕수육도련님고기고기");
        MenuHasOptionDTO odto8 = new MenuHasOptionDTO("청양고추", "탕수육도련님고기고기");

        MenuHasOptionDTO odto9 = new MenuHasOptionDTO("한솥밥 곱빼기", "새치 고기고기");
        MenuHasOptionDTO odto10 = new MenuHasOptionDTO("현미밥 교체", "새치 고기고기");
        MenuHasOptionDTO odto11 = new MenuHasOptionDTO("계란후라이", "새치 고기고기");
        MenuHasOptionDTO odto12 = new MenuHasOptionDTO("청양고추", "새치 고기고기");

        MenuHasOptionDTO odto13 = new MenuHasOptionDTO("한솥밥 곱빼기", "돈치 고기고기");
        MenuHasOptionDTO odto14 = new MenuHasOptionDTO("현미밥 교체", "돈치 고기고기");
        MenuHasOptionDTO odto15 = new MenuHasOptionDTO("계란후라이", "돈치 고기고기");
        MenuHasOptionDTO odto16 = new MenuHasOptionDTO("청양고추", "돈치 고기고기");

        MenuHasOptionDTO odto17 = new MenuHasOptionDTO("한솥밥 곱빼기", "제육 김치찌개 정식");
        MenuHasOptionDTO odto18 = new MenuHasOptionDTO("현미밥 교체", "제육 김치찌개 정식");
        MenuHasOptionDTO odto19 = new MenuHasOptionDTO("계란후라이", "제육 김치찌개 정식");
        MenuHasOptionDTO odto20 = new MenuHasOptionDTO("청양고추", "제육 김치찌개 정식");

        MenuHasOptionDTO odto21 = new MenuHasOptionDTO("한솥밥 곱빼기", "제육 김치 부대찌개 정식");
        MenuHasOptionDTO odto22 = new MenuHasOptionDTO("현미밥 교체", "제육 김치 부대찌개 정식");

        MenuHasOptionDTO odto23 = new MenuHasOptionDTO("한솥밥 곱빼기", "탕수육도련님고기고기");
        MenuHasOptionDTO odto24 = new MenuHasOptionDTO("현미밥 교체", "탕수육도련님고기고기");

        dtos2.add(odto1);
        dtos2.add(odto2);
        dtos2.add(odto3);
        dtos2.add(odto4);
        dtos2.add(odto5);
        dtos2.add(odto6);
        dtos2.add(odto7);
        dtos2.add(odto8);
        dtos2.add(odto9);
        dtos2.add(odto10);
        dtos2.add(odto11);
        dtos2.add(odto12);
        dtos2.add(odto13);
        dtos2.add(odto14);
        dtos2.add(odto15);
        dtos2.add(odto16);
        dtos2.add(odto17);
        dtos2.add(odto18);
        dtos2.add(odto19);
        dtos2.add(odto20);
        dtos2.add(odto21);
        dtos2.add(odto22);
        dtos2.add(odto23);
        dtos2.add(odto24);

        MenuHasOptionDAO menuHasOptionDAO = new MenuHasOptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuHasOptionDAO.insertMenuOption(dtos2);
    }

    public static void test5() {
        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        MenuOptionView view = new MenuOptionView();
        view.printAll(menuDAO.showMenu());
    }

    public static void test6() { // (수정했어 대현아^^)
        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuDAO.updateMenu("돈까스도련님고기고기", "돈까스고기고기", 6500);
    }

    public static void test7() {
        String userID1 = "cust1";
        String userID2 = "cust2";
        String storeName = "한솥도시락 금오공대점";
        String menuName1 = "돈까스고기고기";
        String menuName2 = "새치 고기고기";
        String menuName3 = "돈치 고기고기";
        String optionName1 = "한솥밥 곱빼기";
        String optionName2 = "계란후라이";
        String optionName3 = "청양고추";

        OptionDAO optionDAO = new OptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        OrderDTO dto1 = new OrderDTO(userID1, storeName);
        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderDAO.makeOrder(dto1);
        int orderId = dto1.getOrder_id();

        OrderedMenuDTO dto2 = new OrderedMenuDTO(orderId, menuName1);
        OrderedMenuDAO orderedMenuDAO = new OrderedMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderedMenuDAO.orderMenu(dto2);
        int orderedMenuId = dto2.getOrdered_menu_id();

        OrderedOptionDTO dto3 = new OrderedOptionDTO(orderedMenuId, optionName1);
        OrderedOptionDTO dto4 = new OrderedOptionDTO(orderedMenuId, optionName2);
        OrderedOptionDAO orderedOptionDAO = new OrderedOptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderedOptionDAO.orderedOption(dto3);
        orderedOptionDAO.orderedOption(dto4);

        int o1 = optionDAO.getOptionPrice(dto3.getOption_name());
        int o2 = optionDAO.getOptionPrice(dto4.getOption_name());
        int m1 = menuDAO.getMenuPrice(dto2.getMenu_name());
        orderedMenuDAO.updatePrice(orderedMenuId, o1+o2+m1);
        orderDAO.updatePriceSum(orderId, o1+o2+m1);


        OrderDTO dto5 = new OrderDTO(userID1, storeName);
        orderDAO.makeOrder(dto5);
        int orderId2 = dto5.getOrder_id();

        OrderedMenuDTO dto6 = new OrderedMenuDTO(orderId2, menuName2);
        orderedMenuDAO.orderMenu(dto6);
        int orderedMenuID2 = dto6.getOrdered_menu_id();

        OrderedOptionDTO dto7 = new OrderedOptionDTO(orderedMenuID2, optionName1);
        orderedOptionDAO.orderedOption(dto7);

        int o3 = optionDAO.getOptionPrice(dto7.getOption_name());
        int m2 = menuDAO.getMenuPrice(dto6.getMenu_name());
        orderedMenuDAO.updatePrice(orderedMenuID2, o3+m2);
        orderDAO.updatePriceSum(orderId2, o3+m2);



        OrderDTO dto8 = new OrderDTO(userID1, storeName);
        orderDAO.makeOrder(dto8);
        int orderId3 = dto8.getOrder_id();

        OrderedMenuDTO dto9 = new OrderedMenuDTO(orderId3, menuName2);
        orderedMenuDAO.orderMenu(dto9);
        int orderedMenuID3 = dto9.getOrdered_menu_id();

        OrderedOptionDTO dto10 = new OrderedOptionDTO(orderedMenuID3, optionName1);
        orderedOptionDAO.orderedOption(dto10);

        int o4 = optionDAO.getOptionPrice(dto10.getOption_name());
        int m3 = menuDAO.getMenuPrice(dto9.getMenu_name());
        orderedMenuDAO.updatePrice(orderedMenuID3, o4+m3);
        orderDAO.updatePriceSum(orderId3, o4+m3);



        OrderDTO dto11 = new OrderDTO(userID2, storeName);
        orderDAO.makeOrder(dto11);
        int orderId4 = dto11.getOrder_id();

        OrderedMenuDTO dto12 = new OrderedMenuDTO(orderId4, menuName3);
        orderedMenuDAO.orderMenu(dto12);
        int orderedMenuID4 = dto12.getOrdered_menu_id();

        OrderedOptionDTO dto13 = new OrderedOptionDTO(orderedMenuID4, optionName3);
        orderedOptionDAO.orderedOption(dto13);

        int o5 = optionDAO.getOptionPrice(dto13.getOption_name());
        int m4 = menuDAO.getMenuPrice(dto12.getMenu_name());
        orderedMenuDAO.updatePrice(orderedMenuID4, o5+m4);
        orderDAO.updatePriceSum(orderId4, o5+m4);
    }

    public static void test8() {
        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        OrderView orderView = new OrderView();
        orderView.printAll(orderDAO.getOrderList("한솥도시락 금오공대점"));
    }
    public static void test9() {
        int order_id1 = 1;
        int order_id2 = 2;
        int order_id3 = 3;
        int newState = 2;

        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderDAO.updateState(order_id1, newState);
        orderDAO.updateState(order_id2, newState);
        orderDAO.updateState(order_id3, newState);
    }

    public static void test10(int orderId) {
        int state;
        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        state = orderDAO.getOrderState(orderId);
        if (state == 0) {
            orderDAO.cancelOrder(orderId);
            System.out.printf("주문을 취소하였습니다.\n", orderId);
        }
        else {
            System.out.print("이미 배달중인 주문은 취소가 불가능합니다.\n");
        }
    }

    public static void test11() {
        String userID = "cust1";
        String storeName = "한솥도시락 금오공대점";
        String menuName = "돈치스팸 김치 부대찌개 정식";
        int stock;
        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        stock = menuDAO.getStock(menuName);

        if (stock > 0) {
            OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
            OrderDTO orderDTO = new OrderDTO(userID, storeName);
            orderDAO.makeOrder(orderDTO);
            int order_id = orderDTO.getOrder_id();

            OrderedMenuDTO orderedMenuDTO = new OrderedMenuDTO(order_id, menuName);
            OrderedMenuDAO orderedMenuDAO = new OrderedMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
            orderedMenuDAO.orderMenu(orderedMenuDTO);
            int orderedMenuId = orderedMenuDTO.getOrdered_menu_id();
            menuDAO.updateStock(menuName, stock-1);

            int m1 = menuDAO.getMenuPrice(orderedMenuDTO.getMenu_name());
            orderedMenuDAO.updatePrice(orderedMenuId, m1);
            orderDAO.updatePriceSum(order_id, m1);
            System.out.println("주문 성공");
        } else {
            System.out.println("재고가 없어 주문 실패");
        }
    }

    public static void test12() {
        int order_id1 = 1;
        int order_id2 = 2;
        int order_id3 = 3;
        int newState = 3;

        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderDAO.updateState(order_id1, newState);
        orderDAO.updateState(order_id2, newState);
        orderDAO.updateState(order_id3, newState);

        OrderDAO orderDAO2 = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        OrderView orderView = new OrderView();
        orderView.printAll(orderDAO2.getOrderFinishList("한솥도시락 금오공대점"));
    }

    public static void test13() {
        int orderId1 = 1;
        int orderId2 = 2;
        int orderId3 = 3;
        String review1 = "진짜 맛있네요";
        String review2 = "잘먹었습니다.";
        String review3 = "정말 맛있었어요";

        ReviewDAO reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        ReviewDTO reviewDTO1 = new ReviewDTO(review1, 3, orderId1);
        reviewDAO.writeReview(reviewDTO1);

        ReviewDTO reviewDTO2 = new ReviewDTO(review2, 4, orderId2);
        reviewDAO.writeReview(reviewDTO2);

        ReviewDTO reviewDTO3 = new ReviewDTO(review3, 5, orderId3);
        reviewDAO.writeReview(reviewDTO3);
    }

    public static void test14(int i) {
        List<Map<String, Object>> list = null;
        String user_id = "cust1";
        int crtPage = i;
        int lastPage = 0;
        ReviewDAO reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        lastPage = reviewDAO.getReviewNum(user_id);

        list = reviewDAO.showReview(user_id, crtPage);
        ReviewView.printAll(list, crtPage , lastPage);
    }
}
