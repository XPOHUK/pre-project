package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new UserDaoHibernateImpl());
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte)10);
        userService.saveUser("Vasiliy", "Vasiliev", (byte)15);
        userService.saveUser("Petr", "Petrov", (byte)30);
        userService.saveUser("Vasya", "Pupkin", (byte)100);
        userService.saveUser("Vasya", "Pupkin II", (byte)100);
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.removeUserById(3);
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.removeUserById(3);
        userService.cleanUsersTable();
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.dropUsersTable();
        userService.dropUsersTable();
    }
}
