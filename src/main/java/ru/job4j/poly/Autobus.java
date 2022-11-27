package ru.job4j.poly;

public class Autobus implements Vehicle {
    @Override
    public void move() {
        System.out.println("передаем за проезд");
    }

    public static void main(String[] args) {
        Vehicle[] vehicles = new Vehicle[]{new Airplane(), new Autobus(), new Train()};
        for (Vehicle v : vehicles) {
            v.move();
        }
    }
}
