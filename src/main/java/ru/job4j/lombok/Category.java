package ru.job4j.lombok;

import lombok.*;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Category {
    @Getter
    @NonNull
    private int id;
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private String name;

    public static void main(String[] args) {
        Category c = new Category(1);
    }
}