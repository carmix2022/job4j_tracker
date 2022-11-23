package ru.job4j.pojo;
import java.util.Date;

public class College {
    public static void main(String[] args) {
        Student student = new Student();
        student.setFullName("Ivanov Petr Vasilievitch");
        student.setGroup(6612);
        student.setReceiptDate(new Date(999999999999L));
        System.out.println(student.getFullName() + System.lineSeparator() + student.getGroup() + "\n" + student.getReceiptDate());
    }
}
