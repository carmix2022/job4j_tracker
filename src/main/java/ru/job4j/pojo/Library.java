package ru.job4j.pojo;

import java.sql.SQLOutput;

public class Library {
    public static void main(String[] args) {
        Book book1 = new Book("Kolobok", 30);
        Book book2 = new Book("Repka", 20);
        Book book3 = new Book("Kapital", 600);
        Book book4 = new Book("Clean Code", 500);
        Book[] books = new Book[4];
        books[0] = book1;
        books[1] = book2;
        books[2] = book3;
        books[3] = book4;
        for (int i = 0; i < books.length; i++) {
            Book bk = books[i];
            System.out.println(bk.getName() + " - " + bk.getCount());
        }
        Book tmp = books[0];
        books[0] = books[3];
        books[3] = tmp;
        System.out.println("\nПосле перестановки:");
        for (int i = 0; i < books.length; i++) {
            Book bk = books[i];
            System.out.println(bk.getName() + " - " + bk.getCount());
        }
        System.out.println("\nКниги с именем \"Clean Code\":");
        for (int i = 0; i < books.length; i++) {
            Book bk = books[i];
            if (bk.getName() == "Clean Code") {
                System.out.println(bk.getName() + " - " + bk.getCount());
            }
        }
    }
}
