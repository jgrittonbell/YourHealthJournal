package com.grittonbelldev.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {

    @Test
    void testJdbcConnection() {
        String url = "jdbc:mysql://awseb-e-mee3mppqez-stack-awsebrdsdatabase-p5kvbeihguiy.cr288w4aoup0.us-east-2.rds.amazonaws.com:3306/YourHealthJournal";
        String username = "username";
        String password = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            assertNotNull(conn, "Connection should not be null");
            System.out.println("✅ JDBC connection successful.");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ JDBC connection failed: " + e.getMessage());
        }
    }
}

