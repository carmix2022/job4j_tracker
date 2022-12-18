package ru.job4j.tracker;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;

public class ItemAscByNameTest {

    @Test
    public void whenSortAscByName() {
        List<Item> items = Arrays.asList(
                        new Item(1, "one"),
                        new Item(2, "two"),
                        new Item(3, "three")
        );
        Collections.sort(items, new ItemAscByName());
        assertThat(items).isEqualTo(List.of(
                new Item(1, "one"),
                new Item(3, "three"),
                new Item(2, "two")
        ));
    }
}