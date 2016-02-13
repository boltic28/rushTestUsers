package ru.javarush.rushtest.users.backend.model;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCconnector {
    private static final String USER_NAME = "root";
    private static final String USER_PASS = "root";
    private static final String USER_URL = "jdbc:mysql://localhost:3306/test";
    private Connection connection;
    private Driver driver;

    public JDBCconnector() {
        init();
    }

    private void init() {

        try {
            driver = new FabricMySQLDriver();
            Class.forName("com.mysql.jdbc.Driver");
        }catch (SQLException e) {
            System.out.println("error SQL");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            DriverManager.registerDriver(driver);
        }catch (SQLException e) {
            System.out.println("drive error");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(USER_URL, USER_NAME, USER_PASS); // change pass to constant
            System.out.println("Base connected");
        }catch (SQLException e) {
            System.out.println("connect error");
        }

    }

    public Connection getConnection() {
        return connection;
    }
}
