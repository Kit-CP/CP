package org.example;

import Database.persistence.MyBatisConnectionFactory;
import Database.persistence.dao.OrderDAO;
import Database.persistence.dao.UserDAO;
import Database.persistence.dto.*;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws IOException {
        List<MenuSalesDTO> list = new ArrayList<>();
        list.add(new MenuSalesDTO("name", 1, 1));
        list.add(new MenuSalesDTO("name1", 1, 1));
        list.add(new MenuSalesDTO("name2", 1, 1));
        byte[] bytes = new byte[0];
        for ( MenuSalesDTO dto : list ) {
                bytes = ArrayUtils.addAll(bytes, dto.getBytes());
        }
    public static void main(String args[]){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_ID("hodu42");
        userDTO.setUser_name("김남식");
        userDTO.setUser_phone("010-1111-2222");
        userDTO.setUser_PW("");

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bis);

        for ( int i = 0; i < 3; i++ ) {
            MenuSalesDTO dto = MenuSalesDTO.readMenuSalesDTO(dis);
            System.out.println(dto.toString());
        }
        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        userDAO.updateInfor("hodu", userDTO);
    }
}