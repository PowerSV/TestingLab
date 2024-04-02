package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.MultiPriorityBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class Processor extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    private ProcessingTask taskInProcess;
    private final ExecutorService executor;
//    private final BlockingQueue<AbstractTask> runningQueue;
    private final MultiPriorityBlockingQueue readyQueue;
//    private final TaskManager taskManager;

//    public Processor(BlockingQueue<AbstractTask> runningQueue, TaskManager taskManager) {
//        this.executor = Executors.newSingleThreadExecutor();
//        this.taskManager = taskManager;
//        this.runningQueue = runningQueue;
//    }
    public Processor(MultiPriorityBlockingQueue readyQueue) {
        this.executor = Executors.newSingleThreadExecutor();
        this.readyQueue = readyQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            AbstractTask nextTask = readyQueue.takeFromReadyState();
            while (true) {
                if (taskInProcess == null || taskInProcess.isCanceled() || taskInProcess.isDone()) {
                    putTask(nextTask);
                    break;
                } else if (taskInProcess.getTask().getPriority() < nextTask.getPriority()) {
                    preempt(nextTask);
                    break;
                }
                    Thread.yield();
            }
            Thread.yield();
        }
    }

    private void putTask(AbstractTask task) {
        LOGGER.info("Start process task " + task);

        taskInProcess = new ProcessingTask(task, executor.submit(task));
    }

    private void preempt(AbstractTask task) {
        LOGGER.info(taskInProcess.getTask() + " -> " + task);

        taskInProcess.cancel();
        AbstractTask prevTask = taskInProcess.getTask();
        putTask(task);
//        taskManager.putInReadyStateBlocking(prevTask);
        readyQueue.putInReadyStateBlocking(prevTask);
    }

    private static class ProcessingTask {
        AbstractTask task;
        Future<?> future;

        public ProcessingTask(AbstractTask task, Future<?> future) {
            this.task = task;
            this.future = future;
        }

        public AbstractTask getTask() {
            return task;
        }

        public void cancel() {
            future.cancel(true);
        }

        public boolean isDone() {
            return future.isDone();
        }

        public boolean isCanceled() {
            return future.isCancelled();
        }
    }
}