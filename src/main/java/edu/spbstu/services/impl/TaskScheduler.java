package edu.spbstu.services.impl;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.MultiPriorityBlockingQueue;
import edu.spbstu.services.Processor;
import edu.spbstu.services.TaskManager;
import edu.spbstu.transporters.SimpleTransporter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TaskScheduler implements TaskManager {
    private static final int MAX_QUEUE_CAPACITY = 64;

    private final BlockingQueue<AbstractTask> waitingQueue = new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY);
    private final BlockingQueue<AbstractTask> suspendedQueue = new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY);
    private final MultiPriorityBlockingQueue readyQueue;

    public TaskScheduler(int numOfPriorities) {
        readyQueue = new MultiPriorityBlockingQueue(numOfPriorities);
        new SimpleTransporter(suspendedQueue, readyQueue::put).start();

        new Processor(this).start();
    }

    public void put(AbstractTask task) throws InterruptedException {
        suspendedQueue.put(task);
    }

    @Override
    public void putInReadyStateBlocking(AbstractTask task) {
        readyQueue.put(task);
    }

    @Override
    public void putInReadyStateNonBlocking(AbstractTask task) {
        readyQueue.add(task);
    }

    @Override
    public AbstractTask peekFromReadyState() {
        return readyQueue.peek();
    }

    @Override
    public AbstractTask takeFromReadyState() {
        return readyQueue.take();
    }
}

