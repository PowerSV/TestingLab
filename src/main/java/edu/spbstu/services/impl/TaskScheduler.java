package edu.spbstu.services.impl;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.MultiPriorityBlockingQueue;
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

public class TaskScheduler {
    private static final int MAX_QUEUE_CAPACITY = 64;

    private final BlockingQueue<AbstractTask> waitingQueue = new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY);
    private final BlockingQueue<AbstractTask> suspendedQueue = new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY);
    private final MultiPriorityBlockingQueue readyQueue;
//    private final BlockingQueue<AbstractTask> runningQueue = new SynchronousQueue<>();

    public TaskScheduler(int numOfPriorities) {
//        new ReadyToRunningTransporter(this::takeFromReadyState, this::putInRunningState).start();
        readyQueue = new MultiPriorityBlockingQueue(numOfPriorities);
        new SimpleTransporter(suspendedQueue, readyQueue::putInReadyStateBlocking).start();

//        new Processor(readyQueue, this).start();
        new Processor(readyQueue).start();
    }

    public void put(AbstractTask task) throws InterruptedException {
        suspendedQueue.put(task);
    }

//    @Override
//    public void putInRunningState(AbstractTask task) {
//        try {
//            runningQueue.put(task);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Override
//
//
//    @Override

}

