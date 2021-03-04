package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), lastname VARCHAR(20), age TINYINT UNSIGNED)";
        try (Connection conn = Util.getConnection()){
            Statement statement =  conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE users";
        try (Connection conn = Util.getConnection()){
            Statement statement =  conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        int rs = 0;
        String query = "INSERT INTO users (name,lastname,age) VALUES (?, ?, ?)";
        try (Connection conn = Util.getConnection()){
            PreparedStatement pStatement =  conn.prepareStatement(query);
            pStatement.setString(1, name);
            pStatement.setString(2, lastName);
            pStatement.setByte(3, age);
            rs = pStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
        if (rs == 1) {
            System.out.printf("User с именем – %s добавлен в базу данных.%n", name);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = Util.getConnection()){
            PreparedStatement pStatement =  conn.prepareStatement(query);
            pStatement.setLong(1, id);
            pStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Util.getConnection()){
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
        String query = "TRUNCATE TABLE users";
        try (Connection conn = Util.getConnection()){
            Statement statement =  conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }
}
