package com.example.oltest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import redis.clients.jedis.Jedis;

public class ConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continueQuery;
        do {
            System.out.println("请输入uid：");
            int uid = scanner.nextInt();
            System.out.println("请输入business：");
            String business = scanner.next();

            String status = getStatusByUidAndBusiness(uid, business);
            if (status == null) {
                System.out.println("没有匹配数据");
            } else {
                System.out.println("status为：" + status);
            }
            System.out.println("你还要继续查询吗？(y/n)");
            continueQuery = scanner.next();
        }while("y".equalsIgnoreCase(continueQuery));
        scanner.close();
        System.out.println("程序已结束。");
    }

    private static String getStatusByUidAndBusiness(int uid, String business) {
        String status = null;
        Jedis jedis = new Jedis("localhost", 6379);
        String key = uid + ":" + business;
        if (jedis.exists(key)) {
            status = jedis.get(key);
            System.out.println("数据从Redis中获取");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bwlist", "root", "123456");

                String sql = "SELECT status FROM bwtest WHERE uid = ? AND business = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, uid);
                pstmt.setString(2, business);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    status = rs.getString("status");
                    System.out.println("数据从Mysql中获取");
                } else{
                    System.out.println("在 MySQL 中未找到数据");
                    status = "not found";
                }
                jedis.set(key, status);
                jedis.expire(key, 300);

                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        jedis.close();
        return status;
    }
}
