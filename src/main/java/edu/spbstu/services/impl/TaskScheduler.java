package edu.spbstu.services.impl;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.services.Processor;
import edu.spbstu.services.TaskManager;
import edu.spbstu.transporters.ReadyToRunningTransporter;
import edu.spbstu.transporters.SimpleTransporter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskScheduler implements TaskManager {
    private static final int MAX_QUEUE_CAPACITY = 64;
    private static final int READY_QUEUE_CAPACITY = 8;

    private final List<BlockingQueue<AbstractTask>> readyQueue = new ArrayList<>();
    private int readyQueueSize = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition isFullCondition = lock.newCondition();
    private final Condition isEmptyCondition = lock.newCondition();

    private final BlockingQueue<AbstractTask> waitingQueue = new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY);
    private final BlockingQueue<AbstractTask> suspendedQueue = new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY);
    private final BlockingQueue<AbstractTask> runningQueue = new SynchronousQueue<>();

    public TaskScheduler(int numOfPriorities) {
        for (int i = 0; i < numOfPriorities; i++) {
            readyQueue.add(new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY));
        }

        new ReadyToRunningTransporter(this::takeFromReadyState, this::putInRunningState).start();
        new SimpleTransporter(suspendedQueue, this::putInReadyStateBlocking).start();

        new Processor(runningQueue, this).start();
    }

    public void put(AbstractTask task) throws InterruptedException {
        suspendedQueue.put(task);
    }

    public AbstractTask takeFromReadyState() {
        lock.lock();
        AbstractTask task = null;
        try {
            while (readyQueueSize == 0) {
                isEmptyCondition.await();
            }
            for (int i = readyQueue.size() - 1; i >= 0; i--) {
                BlockingQueue<AbstractTask> queue = readyQueue.get(i);
                if (!queue.isEmpty()) {
                    task = queue.take();
                    readyQueueSize--;
                    break;
                }
            }
            isFullCondition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        return task;
    }

    @Override
    public void putInRunningState(AbstractTask task) {
        try {
            runningQueue.put(task);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putInReadyStateBlocking(AbstractTask task) {
        lock.lock();
        try {
            while (readyQueueSize >= READY_QUEUE_CAPACITY) {
                isFullCondition.await();
            }

            readyQueue.get(task.getPriority()).put(task);
            readyQueueSize++;
            isEmptyCondition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void putInReadyStateNonBlocking(AbstractTask task) {
        lock.lock();
        try {
            readyQueue.get(task.getPriority()).put(task);
            isEmptyCondition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

