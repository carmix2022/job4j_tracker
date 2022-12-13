package ru.job4j.collection;

import java.util.HashMap;

public class UsageMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("q@yandex.ru", "Ivanov Ivan");
        map.put("er@yandex.ru", "Petrov Petr");
        map.put("er@yandex.ru", "Sidorov Sidr");
        for (String key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }
    }
}
