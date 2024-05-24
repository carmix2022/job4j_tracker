package ru.job4j.tracker.store;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.StartUI;
import ru.job4j.tracker.Store;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {

    private Connection cn;

    public SqlTracker() throws SQLException, ClassNotFoundException {
//        init();
        cn = loadConnection();
    }

    public SqlTracker(Connection cn) {
        this.cn = cn;
    }

    private static String loadSysEnvIfNullThenConfig(String sysEnv, String key, Properties config) {
        String value = System.getenv(sysEnv);
        if (value == null) {
            value = config.getProperty(key);
        }
        return value;
    }

    private static Connection loadConnection() throws ClassNotFoundException, SQLException {
        System.out.println("start method loadConnection");
        var config = new Properties();
        try (InputStream in = StartUI.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            config.load(in);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        String url = loadSysEnvIfNullThenConfig("JDBC_URL", "url", config);
        String username = loadSysEnvIfNullThenConfig("JDBC_USERNAME", "username", config);
        String password = loadSysEnvIfNullThenConfig("JDBC_PASSWORD", "password", config);
        String driver = loadSysEnvIfNullThenConfig("JDBC_DRIVER", "driver-class-name", config);

        System.out.println("Connecting to database with the following settings:");
        System.out.println("URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password); // Be careful with logging passwords in a real application
        System.out.println("Driver: " + driver);

        Class.forName(driver);
        return DriverManager.getConnection(
                url, username, password
        );
    }

//    private void init() {
//        try (InputStream in = new FileInputStream("db/liquibase.properties")) {
//            Properties config = new Properties();
//            config.load(in);
//            Class.forName(config.getProperty("driver-class-name"));
//            cn = DriverManager.getConnection(
//                    config.getProperty("url"),
//                    config.getProperty("username"),
//                    config.getProperty("password")
//            );
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }

    private Item createItem(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getTimestamp("created").toLocalDateTime()
        );
    }

    @Override
    public void close() throws SQLException {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement statement =
                     cn.prepareStatement("INSERT INTO items(name, created) VALUES (?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean result = false;
        try (PreparedStatement statement =
                     cn.prepareStatement("UPDATE items SET name = ?, created = ? WHERE id = ?")) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            statement.setInt(3, id);
            result = statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (PreparedStatement statement =
                     cn.prepareStatement("DELETE FROM items WHERE id = ?")) {
            statement.setInt(1, id);
            result = statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> listItems = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("SELECT * FROM items")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listItems.add(createItem(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listItems;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> listItems = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("SELECT * FROM items WHERE name = ?")) {
            statement.setString(1, key);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listItems.add(createItem(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listItems;
    }

    @Override
    public Item findById(int id) {
        Item rsl = null;
        try (PreparedStatement statement = cn.prepareStatement("SELECT * FROM items WHERE id = ? LIMIT 1")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    rsl = createItem(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}