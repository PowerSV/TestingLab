package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Processor extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    private ProcessingTask taskInProcess;
    private final ExecutorService executor;
    private final TaskManager taskManager;

    public Processor(TaskManager taskManager) {
        this.executor = Executors.newSingleThreadExecutor();
        this.taskManager = taskManager;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            while (true) {
                AbstractTask nextTask = taskManager.peekFromReadyState();
                if (taskInProcess == null || taskInProcess.isCanceled() || taskInProcess.isDone()) {
                    putTask(taskManager.takeFromReadyState());
                    break;
                } else if (taskInProcess.getTask().getPriority() < nextTask.getPriority()) {
                    preempt(taskManager.takeFromReadyState());
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
        taskManager.putInReadyStateBlocking(prevTask);
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
