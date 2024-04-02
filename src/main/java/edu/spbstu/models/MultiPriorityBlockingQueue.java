package edu.spbstu.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiPriorityBlockingQueue {
    private static final int READY_QUEUE_CAPACITY = 8;

    private final List<BlockingQueue<AbstractTask>> readyQueue = new ArrayList<>();
    private int readyQueueSize = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition isFullCondition = lock.newCondition();
    private final Condition isEmptyCondition = lock.newCondition();

    public MultiPriorityBlockingQueue(int numOfPriorities) {
        for (int i = 0; i < numOfPriorities; i++) {
            readyQueue.add(new ArrayBlockingQueue<>(64));
        }
    }

    public AbstractTask take() {
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

    public void put(AbstractTask task) {
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

    public void add(AbstractTask task) {
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

    public AbstractTask peek() {
        lock.lock();
        AbstractTask task = null;
        try {
            while (readyQueueSize == 0) {
                isEmptyCondition.await();
            }
            for (int i = readyQueue.size() - 1; i >= 0; i--) {
                BlockingQueue<AbstractTask> queue = readyQueue.get(i);
                if (!queue.isEmpty()) {
                    task = queue.peek();
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        return task;
    }

}
