package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public final class Util {
    private static final Properties PROPERTIES = new Properties();
    private static final String URL_KEY = "db.url";
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";

    private Util() {
    }

    static {
        loadProperties();
    }

    public static void loadProperties() {
        try (InputStream inputStream = Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTIES.getProperty(URL_KEY),
                PROPERTIES.getProperty(USERNAME_KEY),
                PROPERTIES.getProperty(PASSWORD_KEY));
    }

    public static SessionFactory getSessionFactory() {
        try {
            return new Configuration()
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        return session.createQuery(criteria).getResultList();
    }
}
