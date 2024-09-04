package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users
                (
                    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
                    name VARCHAR(255),
                    last_name VARCHAR(255),
                    age SMALLINT
                );
                """;
        executeUpdate(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        executeUpdate(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) VALUES (?,?,?)";

        try (var connection = Util.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            if (statement.executeUpdate() > 0) {
                System.out.println("User с именем " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (var connection = Util.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = """
                SELECT  id,
                        name,
                        last_name,
                        age
                FROM users
                """;

        try (var connection = Util.getConnection();
             var statement = connection.createStatement()) {

            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        executeUpdate(sql);
    }

    public void executeUpdate(String query) {
        try (var connection = Util.getConnection();
             var statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
