package ru.job4j.oop;

public class Jukebox {

    public void music(int position) {
        String rsl = switch (position) {
            case 1 -> "Пусть бегут неуклюже";
            case 2 -> "Спокойной ночи";
            default -> "Песня не найдена";
        };
        System.out.println(rsl);
    }

    public static void main(String[] args) {
        Jukebox device = new Jukebox();
        device.music(1);
        device.music(2);
        device.music(3);
    }
}
