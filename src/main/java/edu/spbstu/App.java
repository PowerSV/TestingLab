package edu.spbstu;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.BaseTask;
import edu.spbstu.models.ExtendedTask;
import edu.spbstu.services.impl.TaskScheduler;

import java.util.Random;

public class App {

    // Example with BaseTsk only
//    public static void main(String[] args) throws InterruptedException {
//        TaskScheduler scheduler = new TaskScheduler(4);
//
//        AbstractTask task1 = new BaseTask(0);
//        AbstractTask task2 = new BaseTask(0);
//        AbstractTask task3 = new BaseTask(2);
//        AbstractTask task4 = new BaseTask(3);
//        AbstractTask task5 = new BaseTask(1);
//
//        scheduler.put(task1);
//        scheduler.put(task2);
//        Thread.sleep(500);
//        scheduler.put(task3);
//        Thread.sleep(500);
//        scheduler.put(task4);
//        scheduler.put(task5);
//    }

    // Example with ExtendedTask
//    public static void main(String[] args) throws InterruptedException {
//        TaskScheduler scheduler = new TaskScheduler(4);
//
//        AbstractTask extendedTask1 = new ExtendedTask(1, scheduler);
//        AbstractTask task1 = new BaseTask(1);
//
//        scheduler.put(extendedTask1);
//        scheduler.put(task1);
//    }

    public static void main(String[] args) throws InterruptedException {//
        Random random = new Random();

        TaskScheduler scheduler = new TaskScheduler(4); // Создание планировщика

        while (true) {
            int priority = random.nextInt(4);

            AbstractTask newTask = random.nextBoolean()
                    ? new BaseTask(priority)
                    : new ExtendedTask(priority, scheduler);
            scheduler.put(newTask);

            Thread.sleep(1000 + random.nextInt(501));
        }
    }
}
