package ru.job4j.oop;

public class Error {

    boolean active;
    int status;
    String message;

    public Error() {
    }

    public Error(boolean active, int status, String message) {
        this.active = active;
        this.status = status;
        this.message = message;
    }

    public void printInfo() {
        System.out.println(this.active);
        System.out.println(this.status);
        System.out.println(this.message);
    }

    public static void main(String[] args) {
        Error one = new Error();
        Error two = new Error(true, 10, "error!");
        Error three = new Error(true, 444, "error!!!!");
        one.printInfo();
        two.printInfo();
        three.printInfo();
    }
}
