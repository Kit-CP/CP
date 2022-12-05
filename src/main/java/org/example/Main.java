package org.example;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.OrderDAO;
import Database.persistence.dao.UserDAO;
import Database.persistence.dto.*;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_ID("hodu42");
        userDTO.setUser_name("김남식");
        userDTO.setUser_phone("010-1111-2222");
        userDTO.setUser_PW("");

        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        userDAO.updateInfor("hodu", userDTO);
    }
}