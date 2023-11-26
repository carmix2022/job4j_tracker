package ru.job4j.tracker.store;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.tracker.Item;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

public class SqlTrackerTest {

    private static Connection connection;

    @BeforeAll
    public static void initConnection() {
        try (InputStream in = new FileInputStream("db/liquibase_test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId())).isEqualTo(item);
    }

    @Test
    public void whenSaveItemsAndFindByNameThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item2 = new Item("item2");
        Item item3 = new Item("item2");
        tracker.add(item2);
        tracker.add(item3);
        List<Item> rsl = tracker.findByName(item2.getName());
        assertThat(rsl).hasSize(2)
                .containsOnly(item2, item3);
    }

    @Test
    public void whenSaveItemsAndFindAllThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item4 = new Item("item4");
        Item item5 = new Item("item5");
        tracker.add(item4);
        tracker.add(item5);
        List<Item> rsl = tracker.findAll();
        assertThat(rsl).hasSize(2)
                .containsOnly(item4, item5);
    }

    @Test
    public void whenSaveItemAndDeleteThenDBIsEmpty() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item6 = new Item("item6");
        tracker.add(item6);
        tracker.delete(item6.getId());
        List<Item> rsl = tracker.findAll();
        assertThat(rsl).isEmpty();
    }

    @Test
    public void whenSaveItemAndReplace() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item7 = new Item("item7");
        Item item8 = new Item("item8");
        tracker.add(item7);
        tracker.replace(item7.getId(), item8);
        List<Item> rsl = tracker.findAll();
        assertThat(rsl).hasSize(1)
                .allSatisfy(e -> {
                    assertThat(e.getId()).isEqualTo(item7.getId());
                    assertThat(e.getName()).isEqualTo(item8.getName());
                    assertThat(e.getCreated()).isEqualTo(item8.getCreated());
                });
    }
}