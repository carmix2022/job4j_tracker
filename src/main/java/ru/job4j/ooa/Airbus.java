package ru.job4j.ooa;

public final class Airbus extends Aircraft {
    private final int countEngine;

    private String name;

    public Airbus(String name) {
        this.name = name;
        this.countEngine = "A380".equals(name) ? 4 : 2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printModel() {
        System.out.println("Модель самолета: " + name);
    }

    public void printCountEngine() {
        System.out.println("Количество двигателей равно: " + countEngine);
    }

    @Override
    public String toString() {
        return "Airbus{"
                + "name='" + name + '\''
                + '}';
    }
}