package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Scheduler {
    private final List<BlockingQueue<AbstractTask>> taskQueues;

    public Scheduler() {
        taskQueues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            taskQueues.add(new ArrayBlockingQueue<AbstractTask>(10));
        }
    }

    public void addTask(AbstractTask task) {
        taskQueues.get(task.getPriority()).add(task);
    }

    public AbstractTask getNextTask() {
        for (int i = taskQueues.size() - 1; i >= 0; i--) {
            Queue<AbstractTask> queue = taskQueues.get(i);
            if (!queue.isEmpty()) {
                return queue.poll();
            }
        }
        return null;
    }
}
