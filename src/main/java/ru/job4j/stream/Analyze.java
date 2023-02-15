package ru.job4j.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Analyze {
    public static double averageScore(Stream<Pupil> stream) {
        return stream.flatMapToInt(p -> p.subjects().stream()
                        .mapToInt(Subject::score))
                .average()
                .orElse(-1);
    }

    public static List<Tuple> averageScoreByPupil(Stream<Pupil> stream) {
        return stream.map(p -> new Tuple(p.name(), p.subjects().stream()
                        .mapToInt(Subject::score)
                        .average()
                        .orElse(-1)))
                .collect(Collectors.toList());
    }

    public static List<Tuple> averageScoreBySubject(Stream<Pupil> stream) {
        return stream.flatMap(p -> p.subjects().stream())
                .collect(Collectors.groupingBy(Subject::name, LinkedHashMap::new,
                        Collectors.averagingDouble(Subject::score)))
                .entrySet().stream()
                .map(e -> new Tuple(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public static Tuple bestStudent(Stream<Pupil> stream) {
        return stream.map(p -> new Tuple(p.name(), p.subjects().stream()
                .mapToInt(Subject::score)
                .sum()))
                .max(Comparator.comparing(Tuple::score))
                .orElse(null);
    }

    public static Tuple bestSubject(Stream<Pupil> stream) {
        return stream.flatMap(p -> p.subjects().stream())
                .collect(Collectors.groupingBy(Subject::name,
                        Collectors.summingDouble(Subject::score)))
                .entrySet().stream()
                .map(e -> new Tuple(e.getKey(), e.getValue()))
                .max(Comparator.comparingDouble(Tuple::score))
                .orElse(null);
    }
}