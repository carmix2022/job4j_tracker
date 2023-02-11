package ru.job4j.search;

import java.util.ArrayList;
import java.util.function.Predicate;

public class PhoneDictionary {
    private ArrayList<Person> persons = new ArrayList<>();

    public void add(Person person) {
        this.persons.add(person);
    }

    public ArrayList<Person> find(String key) {
        var result = new ArrayList<Person>();
        Predicate<Person> phone = (x) -> x.getPhone().contains(key);
        Predicate<Person> name = (x) -> x.getName().contains(key);
        Predicate<Person> surname = (x) -> x.getSurname().contains(key);
        Predicate<Person> adress = (x) -> x.getAddress().contains(key);
        var combine = phone.or(name).or(surname).or(adress);
        for (var person : persons) {
            if (combine.test(person)) {
                result.add(person);
            }
        }
        return result;
    }
}