package ru.javarush.rushtest.users.backend.model;

import java.sql.*;
import java.util.*;

public class ModelControl {
    private Connection connection;
    private HashMap<Integer, User> base;

    public ModelControl() {
        this.connection = new JDBCconnector().getConnection();
        readBase();
    }

    public HashMap<Integer, User> getBase() {
        readBase();
        return base;
    }

    public void closeConnection() {
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendToBase(String string) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(string);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createU(String name, int age, String admin, String date) {
        System.out.println(date);  ///delete
        sendToBase(
                String.format("INSERT INTO USER (name, age, isAdmin, createdDate) VALUES ('%s', %d, %d, '%s')",
                        name, age, admin.equalsIgnoreCase("yes") ? 1 : 0, date) //simple.format(date)
        );
    }

    private void readBase() {
        HashMap<Integer, User> userInBase = new HashMap<>();

        try (Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery("SELECT * FROM USER");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setAge(resultSet.getInt(3));
                user.setAdmin(resultSet.getInt(4) == 1 ? "yes" : "no");
                user.setCreateDate(resultSet.getDate(5));
                userInBase.put(user.getId(), user);
            }
            base = userInBase;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateU(String name, int age, String admin, int id, String date) {
        sendToBase(
                String.format("UPDATE user " +
                                "SET name = '%s'" +
                                ", age = %d" +
                                ", isAdmin = %d" +
                                ", createdDate = '%s'" +
                                " WHERE id=%d",
                        name, age, admin.equalsIgnoreCase("yes") ? 1 : 0,date, id )
        );
    }

    public void deleteU(int id) {
        sendToBase(
                String.format("DELETE FROM user WHERE id=%d", id)
        );
    }

    public void generate() {
        new GnrCommand(connection);
        readBase();
    }
}
