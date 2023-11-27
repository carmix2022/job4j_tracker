package ru.job4j.lombok;

import lombok.*;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Category {
    @Getter
    private final int id;
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private String name;
}