package ru.job4j.queue;

import java.util.Deque;

public class ReconstructPhrase {

    private final Deque<Character> descendingElements;

    private final Deque<Character> evenElements;

    public ReconstructPhrase(Deque<Character> descendingElements, Deque<Character> evenElements) {
        this.descendingElements = descendingElements;
        this.evenElements = evenElements;
    }

   private String getEvenElements() {

        StringBuilder rsl = new StringBuilder();
        for (int i = 0, size = evenElements.size(); i < size; i++) {
            if (i % 2 == 0) {
                rsl.append(evenElements.poll());
            } else {
                evenElements.poll();
            }
        }
        return rsl.toString();
    }

    private String getDescendingElements() {
        StringBuilder rsl = new StringBuilder();
        for (int i = 0, size = descendingElements.size(); i < size; i++) {
                rsl.append(descendingElements.pollLast());
        }
        return rsl.toString();
    }

    public String getReconstructPhrase() {
        return getEvenElements() + getDescendingElements();
    }
}