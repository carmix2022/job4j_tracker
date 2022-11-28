package ru.job4j.poly;

public class PolyUsage {
    public static void main(String[] args) {
        Vehicle[] vehicles = new Vehicle[]{new Airplane(), new Autobus(), new Train()};
        for (Vehicle v : vehicles) {
            v.move();
        }
    }
}
