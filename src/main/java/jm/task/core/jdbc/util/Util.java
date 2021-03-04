package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static SessionFactory sessionFactory;

    private static final String userName = "javamentor";
    private static final String password = "javamentor";
    private static final String hostName = "mysql";
    private static final String dbName = "preprousers";
    private static final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
    // реализуйте настройку соеденения с БД
    public static Connection getConnection() throws SQLException,
            ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(connectionURL, userName,
                password);
    }

    public static SessionFactory getSessionFactory(){
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", connectionURL);
        prop.setProperty("hibernate.connection.username", userName);
        prop.setProperty("hibernate.connection.password", password);
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        //prop.setProperty("hibernate.format_sql", "true");
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
}
