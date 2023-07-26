package com.example.oltest.data_insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class data_insert_1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        //加载驱动类
        Class.forName("com.mysql.jdbc.Driver");
        //构建连接参数
        String url = "jdbc:mysql://localhost:3306/bwlist";
        String user = "root";
        String password = "123456";
        //获取连接对象
        Connection connection = DriverManager.getConnection(url,user,password);
        //获取执行者
        Statement statement = connection.createStatement();
        //判断连接对象
        if(connection == null){
            System.out.println("连接失败");
        }else{
            System.out.println("连接成功");

            //添加数据
            String sql = "INSERT INTO bwtest(uid, status, business, create_time, update_time) VALUES(1000, 'close', 'shopping', 111, 111)";
            if (statement.executeUpdate(sql) >=1){
                System.out.println("添加成功");
            }else{
                System.out.println("添加失败");
            }

            connection.close();
        }
    }

}
