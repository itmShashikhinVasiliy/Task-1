package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Sh", (byte) 1);
        userService.saveUser("Vasya", "Hj", (byte) 26);
        userService.saveUser("Tima", "Jk", (byte) 26);
        userService.saveUser("Sasha", "Kl", (byte) 39);

//        userService.removeUserById(3);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
