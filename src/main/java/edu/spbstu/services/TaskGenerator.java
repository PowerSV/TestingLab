package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.BaseTask;

import java.util.concurrent.ThreadLocalRandom;

public class TaskGenerator implements Runnable {
    private final TaskScheduler scheduler;

    public TaskGenerator(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        try {
            // Генерация и добавление 10 задач в планировщик
            int i = 0;
            while (true) {
                if (i > 10) {
                    Thread.sleep(2000);
                }

                int priority = ThreadLocalRandom.current().nextInt(0, 4); // Генерация случайного приоритета
                AbstractTask task = new BaseTask(i, priority);
                scheduler.addTask(task);
                i++;
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("TaskGenerator thread interrupted");
        }
    }
}
