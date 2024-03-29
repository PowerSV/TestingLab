package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Scheduler {
    private final List<Queue<AbstractTask>> taskQueues;

    public Scheduler(int numPriorities) {
        taskQueues = new ArrayList<>();
        for (int i = 0; i < numPriorities; i++) {
            taskQueues.add(new LinkedList<>());
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
