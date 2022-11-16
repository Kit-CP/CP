package org.example;

import persistence.MyBatisConnectionFactory;
import persistence.dao.*;
import persistence.dto.*;
import view.StoreView;

import java.util.ArrayList;
import java.util.List;

public class ForTest {
    public static void test1() {
        StoreDTO sdto = new StoreDTO();
        sdto.setStore_name("맘스터치");
        sdto.setInformation("싸이버거 최고");
        sdto.setStore_phone("055-271-1234");
        sdto.setUser_ID("test123");
        sdto.setStore_address("경북 구미시 대학로 뭐시기");

        StoreDAO storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        storeDAO.insertStore(sdto);
    }

    public static void test2() {
        StoreDAO storeDAO = new StoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        StoreView storeView = new StoreView();
        List<StoreDTO> all = storeDAO.showAcceptedStore();
        storeView.printAll(all);
    }

    public static void test3() {
        List<OptionDTO> dtos = new ArrayList<>();
        OptionDTO dto1 = new OptionDTO("옵션1", 100, "맘스터치");
        OptionDTO dto2 = new OptionDTO("옵션2", 200, "맘스터치");
        dtos.add(dto1);
        dtos.add(dto2);

        OptionDAO optionDAO = new OptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        optionDAO.insertOptionAll(dtos);
    }

    public static void test4() {
        List<MenuDTO> dtos = new ArrayList<>();
        MenuDTO dto1 = new MenuDTO("싸이버거", "맘스터치", "햄버거", 6000, 0, 10, 0);
        MenuDTO dto2 = new MenuDTO("새우버거", "맘스터치", "햄버거", 5500, 0, 10, 0);
        dtos.add(dto1);
        dtos.add(dto2);

        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuDAO.insertMenuAll(dtos);

        List<OrderedOption> dtos2 = new ArrayList<>();
        OrderedOption dto3 = new OrderedOption("옵션1", "싸이버거");
        OrderedOption dto4 = new OrderedOption("옵션2", "싸이버거");
        dtos2.add(dto3);
        dtos2.add(dto4);

        MenuHasOptionDAO menuHasOptionDAO = new MenuHasOptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuHasOptionDAO.insertMenuOption(dtos2);
    }

    public static void test5() {
        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        System.out.print(menuDAO.showMenu().toString());
    }

    public static void test6() {
        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuDAO.updateMenu("새우버거", "new새우버거", 5050);
    }

    public static void test7() {
        List<OrderDTO> dtos = new ArrayList<>();
        OrderDTO dto1 = new OrderDTO("test123", "맘스터치");
        dtos.add(dto1);

        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderDAO.makeOrder(dtos);

        List<Integer> orderNum = orderDAO.getLastOrderNum();
        System.out.println(orderNum);

        /*List<OrderedMenuDTO> dtos2 = new ArrayList<>();
        OrderedMenuDTO dto2 = new OrderedMenuDTO(orderNum, "");
        dtos2.add(dto2);

        OrderedMenuDAO orderedMenuDAO = new OrderedMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderedMenuDAO.orderMenu(dtos2);

        List<OrderedOptionDTO> dtos3 = new ArrayList<>();
        OrderedOptionDTO dto3 = new OrderedOptionDTO("", "");
        dtos3.add(dto3);

        OrderedOptionDAO orderedOptionDAO = new OrderedOptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        orderedOptionDAO.orderOption(dtos3);*/



    }



}