package edu.spbstu;

import edu.spbstu.models.AbstractTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiPriorityQueue {
    private final List<BlockingQueue<AbstractTask>> queues;

    private static final int MAX_SIZE = 5;
    private int size = 0;

    public MultiPriorityQueue() {
        queues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            queues.add(new LinkedBlockingQueue<>());
        }
    }

    public synchronized void put(AbstractTask task) {
        int priorityLevel = task.getPriority();

        if (priorityLevel >= 0 && priorityLevel < queues.size()) {
            queues.get(priorityLevel).add(task);
            size++;
            notify();
        } else {
            throw new IllegalArgumentException("Invalid priority level");
        }
    }

    public synchronized AbstractTask take() throws InterruptedException {
        for (BlockingQueue<AbstractTask> queue : queues) {
            if (!queue.isEmpty()) {
                size--;
                return queue.poll();
            }
        }

        wait();
        return take();
    }
}
