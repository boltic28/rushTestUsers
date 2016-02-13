package ru.javarush.rushtest.users.backend.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
создает базу на 50 человек
- случайные показатели админа
- имена берутся из списка
- возраст от 10  до 90
 */

public class GnrCommand {

    private static final int NUMBER_USERS = 50;

    private Connection dbConnection;

    private List<String> listRandomNames = new ArrayList<>();
    {
        listRandomNames.add("Sergey");listRandomNames.add("Olga");
        listRandomNames.add("Andrey");listRandomNames.add("Anna");
        listRandomNames.add("Ivan");listRandomNames.add("Elena");
        listRandomNames.add("Fedor");listRandomNames.add("Tanya");
        listRandomNames.add("Petr");listRandomNames.add("Sarah");
    }

    public GnrCommand(Connection dbConnection) {
        this.dbConnection = dbConnection;
        generateBase();
    }

    private void generateBase() {
        for (int i = 0; i < NUMBER_USERS; i++) {
            String insertTableSQL = String.format("INSERT INTO USER (name, age, isAdmin, createdDate) VALUES ('%s', %d, %d, '%s')",
                    listRandomNames.get((int)(Math.random()*listRandomNames.size())),(int)(Math.random()*80+10), (int)(Math.random()*2),
                    "2016.11.26");
            try (Statement statement = dbConnection.createStatement()){

                statement.execute(insertTableSQL);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
