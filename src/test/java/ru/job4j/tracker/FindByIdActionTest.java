package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindByIdActionTest {
    @Test
    public void whenItemWasFoundByIdSuccessfully() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        Item item = tracker.add(new Item("Searching item"));
        FindByIdAction search = new FindByIdAction(out);

        Input input = mock(Input.class);

        when(input.askInt(any(String.class))).thenReturn(item.getId());

        search.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString()).isEqualTo(
                "=== Find item by id ===" + ln
                        + item + ln
        );
    }

    @Test
    public void whenItemWasFoundByNameWithFail() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.add(new Item("Searching item"));
        FindByIdAction search = new FindByIdAction(out);

        Input input = mock(Input.class);

        search.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString()).isEqualTo(
                "=== Find item by id ===" + ln
                        + "Заявка с введенным id: 0 не найдена." + ln
        );
    }
}