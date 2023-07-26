package com.example.oltest.data_insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class data_insert_2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
        //加载驱动类
        Class.forName("com.mysql.jdbc.Driver");
        //构建连接参数
        String url = "jdbc:mysql://localhost:3306/bwlist";
        String user = "root";
        String password = "123456";

        String[] statuses = {"open", "close"};
        String[] businesses = {"shopping", "entertainment", "education"};

        Random random = new Random();

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger ignoredCount = new AtomicInteger();

        int targetSuccessCount = 30000000;
        do {
            // 创建一个线程池
            ExecutorService pool = Executors.newFixedThreadPool(10);
            CountDownLatch latch = new CountDownLatch(10);
            for (int i = 0; i < 10; i++) {
                pool.execute(() -> {
                    //获取连接对象
                    try (Connection connection = DriverManager.getConnection(url, user, password)) {
                        for (int j = 0; j < 100; j++) {
                            //添加数据
                            PreparedStatement statement = connection.prepareStatement(
                                    "INSERT IGNORE INTO bwtest(uid, status, business, create_time, update_time) VALUES (?, ?, ?, ?, ?)");
                            long currentTimeMillis = System.currentTimeMillis();
                            int currentTimeAsInt = (int) (currentTimeMillis / 1000);
                            statement.setInt(1, random.nextInt(1000000000));
                            statement.setString(2, statuses[random.nextInt(statuses.length)]);
                            statement.setString(3, businesses[random.nextInt(businesses.length)]);
                            statement.setInt(4, currentTimeAsInt);
                            statement.setInt(5, currentTimeAsInt);

                            if (statement.executeUpdate() >= 1) {
                                successCount.incrementAndGet();
                            } else {
                                ignoredCount.incrementAndGet();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                });
            }
            // 关闭线程池
            pool.shutdown();
            // 等待所有线程完成任务
            latch.await();
            System.out.println("成功添加的数量：" + successCount.get());
            System.out.println("忽略的数量：" + ignoredCount.get());
        } while(successCount.get() < targetSuccessCount);


    }
}
