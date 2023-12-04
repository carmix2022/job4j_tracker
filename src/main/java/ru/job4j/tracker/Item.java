package ru.job4j.tracker;

import lombok.*;
import ru.job4j.toone.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "items")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String name;

    @EqualsAndHashCode.Exclude
    private LocalDateTime created = LocalDateTime.now().withNano(0);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "item_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> participates = new ArrayList<>();

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item(int id, String name, LocalDateTime created) {
        this.id = id;
        this.name = name;
    }
}