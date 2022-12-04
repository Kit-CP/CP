package org.example;

import Database.persistence.dto.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String args[]){
        NewOrderDTO newOrderDTO = new NewOrderDTO();
        newOrderDTO.setStore_name("한솥도시락 금오공대점");
        newOrderDTO.setUser_ID("hodu");
        List<String> list = new ArrayList<>();
        list.add("돈까스고기고기/한솥밥 곱빼기/계란후라이");
        list.add("새치 고기고기/한솥밥 곱빼기");
        list.add("돈치 고기고기/청양고추/계란후라이");
        newOrderDTO.setMenus_options(list);
        ForTest.test(newOrderDTO);
    }
}