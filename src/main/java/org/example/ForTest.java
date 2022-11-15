package org.example;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MenuDAO;
import persistence.dao.OptionDAO;
import persistence.dao.StoreDAO;
import persistence.dto.MenuDTO;
import persistence.dto.OptionDTO;
import persistence.dto.StoreDTO;
import view.StoreView;

import java.util.ArrayList;
import java.util.List;

public class ForTest {
    public static void test1() {
        StoreDTO sdto = new StoreDTO();
        sdto.setStore_name("더미네임");
        sdto.setInformation("더미인포");
        sdto.setStore_phone("더미폰");
        sdto.setUser_ID("ckswls");
        sdto.setStore_address("더미주소");

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
        OptionDTO dto1 = new OptionDTO("옵션1", 100, "맘터");
        OptionDTO dto2 = new OptionDTO("옵션2", 200, "맘터");
        dtos.add(dto1);
        dtos.add(dto2);

        OptionDAO optionDAO = new OptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        optionDAO.insertOptionAll(dtos);
    }

    public static void test4() {
        List<MenuDTO> dtos = new ArrayList<>();
        MenuDTO dto1 = new MenuDTO("싸이버거", "맘터", "햄버거", 6000, 0, 10, 0);
        MenuDTO dto2 = new MenuDTO("새우버거", "맘터", "햄버거", 5500, 0, 10, 0);
        dtos.add(dto1);
        dtos.add(dto2);

        MenuDAO menuDAO = new MenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        menuDAO.insertMenuAll(dtos);
    }
}
