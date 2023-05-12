package com.how2java.tmall_springboot.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmall_springboot", "root", "admin");
             Statement statement = c.createStatement();) {
            for (int i = 11; i <= 20; ++i) {
                String sqlFormat = "insert into category values(null, '测试分类%d')";
                String sql = String.format(sqlFormat, i);
                statement.execute(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;

    }
}
