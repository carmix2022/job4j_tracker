package ru.job4j.tracker;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemDescByNameTest {

    @Test
    public void whenSortDescByName() {
        List<Item> items = Arrays.asList(
                new Item(1, "one"),
                new Item(2, "two"),
                new Item(3, "three")
        );
        Collections.sort(items, new ItemDescByName());
        assertThat(items).isEqualTo(List.of(
                new Item(2, "two"),
                new Item(3, "three"),
                new Item(1, "one")
        ));
    }
}