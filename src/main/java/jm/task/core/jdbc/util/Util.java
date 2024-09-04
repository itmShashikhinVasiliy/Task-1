package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Util {
    private static final Properties PROPERTIES = new Properties();
    private static final String URL_KEY = "db.url";
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";

    static {
        loadProperties();
    }

    private Util() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection( PROPERTIES.getProperty(URL_KEY),
                                                PROPERTIES.getProperty(USERNAME_KEY),
                                                PROPERTIES.getProperty(PASSWORD_KEY));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void loadProperties() {
        try (InputStream inputStream = Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
