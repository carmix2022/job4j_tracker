package ru.job4j.ex;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FactTest {

    @Test
    public void calc() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Fact().calc(-3);
                });
        assertThat(exception.getMessage()).isEqualTo("N could not be less than 0");
    }
}