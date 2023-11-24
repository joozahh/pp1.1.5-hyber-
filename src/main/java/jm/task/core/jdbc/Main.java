package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = Util.getConnection();
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Anime", "Naruto", (byte) 23);
        us.saveUser("Stas", "Mihailov", (byte) 99);
        us.saveUser("Jafar", "Harisa", (byte) 20);
        List<User> list = us.getAllUsers();
        list.forEach(System.out::println);
        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
