package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String dbUserName = "javamentor";
    private final String dbUserPassword = "javamentor";
    private final String dbHostName = "mysql";
    private final String dbName = "preprousers";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        int rs = sendUpdate("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), lastname VARCHAR(20), age TINYINT UNSIGNED)");
    }

    public void dropUsersTable() {
        int rs = sendUpdate("DROP TABLE users");
    }

    public void saveUser(String name, String lastName, byte age) {
        int rs = sendUpdate("INSERT INTO users (name,lastname,age) VALUES ('"+name+"','"+lastName+"','"+age+"')");
        if (rs == 1) {
            System.out.printf("User с именем – %s добавлен в базу данных.%n", name);
        }
    }

    public void removeUserById(long id) {
        int rs = sendUpdate("DELETE FROM users WHERE id = "+id+";");
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Util.getConnection(dbHostName, dbName, dbUserName, dbUserPassword)){
            Statement statement =  conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users;");
            while (rs.next()){
                User current = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                current.setId(rs.getLong(1));
                users.add(current);
            }
        } catch (SQLException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        int rs = sendUpdate("TRUNCATE TABLE users");
    }

    private int sendUpdate(String query){
        int res = 0;
        try (Connection conn = Util.getConnection(dbHostName, dbName, dbUserName, dbUserPassword)){
            Statement statement =  conn.createStatement();
            res = statement.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
        return res;
    }

}
