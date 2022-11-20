package ru.job4j.oop;

public class BallStory {
    public static void main(String[] args) {
        Ball kolobok = new Ball();
        Wolf volk = new Wolf();
        Hare medved = new Hare();
        Fox lisa = new Fox();
        volk.tryEat(kolobok);
        medved.tryEat(kolobok);
        lisa.tryEat(kolobok);
    }
}
