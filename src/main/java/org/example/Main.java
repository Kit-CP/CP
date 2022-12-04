package org.example;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.UserDAO;
import Database.persistence.dto.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_ID("hodu");
        userDTO.setUser_PW("aaa");
        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        int result = userDAO.signIn(userDTO);

        System.out.println(result);
    }
}