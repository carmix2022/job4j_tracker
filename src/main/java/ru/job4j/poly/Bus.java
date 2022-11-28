package ru.job4j.poly;

public class Bus implements Transport {
    @Override
    public void go() {
        System.out.println("передаем за проезд!");
    }

    @Override
    public void passengers(int count) {
        System.out.println("кол-во пассажиров: " + count);
    }

    @Override
    public double refuel(double fuel) {
        return fuel * 100;
    }
}
