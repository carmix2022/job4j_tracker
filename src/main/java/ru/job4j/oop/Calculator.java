package ru.job4j.oop;

public class Calculator {
    private static int x = 5;

    public static int sum(int y) {
        return x + y;
    }

    public int multiply(int a) {
        return x * a;
    }

    public static int minus(int y) {
        return y - x;
    }

    public double divide(int y) {
        return 1.0 * y / x;
    }

    public double sumAllOperation(int k) {
        return divide(k) + minus(k) + multiply(k) + sum(k);
    }

    public static void main(String[] args) {
        sum(10);
        Calculator calc = new Calculator();
        calc.multiply(5);
        minus(4);
        calc.divide(2);
        double rsl = calc.sumAllOperation(2);
        System.out.println(rsl);
    }
}