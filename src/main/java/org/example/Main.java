package org.example;

import java.sql.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String args[]){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            //Class.forName("com.mysql.jdbc.Driver"); //java 7이후 생략 가능
            Class.forName("com.mysql.cj.jdbc.Driver");
            //String url = "jdbc:mysql://localhost/mydb?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
            String url = "jdbc:mysql://localhost/delivery";
            conn = DriverManager.getConnection(url, "delivery_con", "1234");

            String query = "SELECT * FROM user";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                String id = rs.getString("ID");
                String title = rs.getString("P/W");
                String authority = rs.getString("authority");
                String address = rs.getString("address");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                System.out.printf("%s | %s | %s | %s | %s | %s \n", id,title,authority,address,name,phone);
                System.out.println("-------------------------------------");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e){
            System.out.println("error : " + e);
        }  finally{
            try{
                if(conn != null && !rs.isClosed()){
                    rs.close();
                }
                if(conn != null && !stmt.isClosed()){
                    rs.close();
                }
                if(conn != null && !conn.isClosed()){
                    conn.close();
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}