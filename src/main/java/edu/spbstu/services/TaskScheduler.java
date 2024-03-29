package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskScheduler implements Runnable {
    private final List<Queue<AbstractTask>> taskQueues; // Очереди задач для каждого приоритета
    private AbstractTask currentTask;

    private final int MAX_QUEUE_SIZE = 5; // Максимальный размер очереди задач
    private AtomicInteger readyTasksCount = new AtomicInteger(0);

    public TaskScheduler(int numPriorities) {
        taskQueues = new ArrayList<>(numPriorities);
        for (int i = 0; i < numPriorities; i++) {
            taskQueues.add(new LinkedList<>()); // Создание очередей задач для каждого приоритета
        }
    }

    // Метод для добавления задачи в очередь с указанным приоритетом
    public synchronized void addTask(AbstractTask task) throws InterruptedException {
        int priority = task.getPriority();
        if (readyTasksCount.get() == MAX_QUEUE_SIZE) {
            wait();
        }
        if (priority >= 0 && priority < taskQueues.size()) {
            taskQueues.get(priority).offer(task); // Добавление задачи в соответствующую очередь по приоритету
            readyTasksCount.incrementAndGet();
            notify(); // Уведомление потока ожидающего выполнения задачи
        } else {
            throw new IllegalArgumentException("Invalid priority");
        }
    }

    // Метод для получения следующей задачи для выполнения с учетом приоритета
    public synchronized AbstractTask getNextTask() throws InterruptedException {
        for (int i = taskQueues.size() - 1; i >= 0; i--) {
            Queue<AbstractTask> queue = taskQueues.get(i);
            if (!queue.isEmpty()) {
                readyTasksCount.decrementAndGet();
                notify();
                return queue.poll(); // Получение и удаление следующей задачи из очереди с наивысшим приоритетом
            }
        }
        wait(); // Ожидание новых задач, если очереди пусты
        return null;
    }

    @Override
    public void run() {
        try {
            // Логика планировщика выполнения задач
            while (true) {
                AbstractTask task = getNextTask();
                if (task != null) {
                    currentTask = task;
                    task.executeTask(); // Выполнение задачи
                    currentTask = null;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("TaskScheduler thread interrupted");
        }
    }
}
