package ru.job4j.queue;

import java.util.Queue;

public class AppleStore {
    private final Queue<Customer> queue;

    private final int count;

    public AppleStore(Queue<Customer> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    public String getLastHappyCustomer() {
        if (count == 0) {
            return "";
        }
        for (int i = 0; i < Math.min(count, queue.size()) - 1; i++) {
            queue.poll();
        }
        return queue.poll().name();
    }

    public String getLastUpsetCustomer() {
        if (queue.size() <= count) {
            return "";
        }
        for (int i = 0; i < count; i++) {
            queue.poll();
        }
        return queue.poll().name();
    }
}