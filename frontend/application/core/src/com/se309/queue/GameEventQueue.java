package com.se309.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameEventQueue {

    ConcurrentLinkedQueue<GameEvent> queue;

    public GameEventQueue() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public void queue(GameEvent e) {
        queue.add(e);
    }

    public GameEvent dequeue() {

        if (queue.isEmpty()) {
            return null;
        }
        return queue.remove();
    }

    public boolean hasNext() {
        return queue.peek() != null;
    }

}
