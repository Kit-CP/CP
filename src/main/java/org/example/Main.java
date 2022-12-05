package org.example;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.OrderDAO;
import Database.persistence.dao.UserDAO;
import Database.persistence.dto.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        NewOrderDTO newOrderDTO = new NewOrderDTO();
        newOrderDTO.setUser_ID("hodu");
        newOrderDTO.setStore_name("한솥도시락 금오공대점");
        newOrderDTO.setMenus_options("돈까스고기고기/한솥밥 곱빼기/계란후라이$새치 고기고기/한솥밥 곱빼기$새치 고기고기/한솥밥 곱빼기$돈치 고기고기/청양고추");
        OrderDAO orderDAO = new OrderDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        boolean result = orderDAO.newMakeOrder(newOrderDTO);

        System.out.println(result);
    }
}