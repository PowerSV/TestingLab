package edu.spbstu;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.BaseTask;
import edu.spbstu.services.impl.TaskScheduler;

public class App {

    public static void main(String[] args) throws InterruptedException {
        TaskScheduler scheduler = new TaskScheduler(4);

        AbstractTask task1 = new BaseTask(0);
        AbstractTask task2 = new BaseTask(2);
        AbstractTask task3 = new BaseTask(3);
        AbstractTask task4 = new BaseTask(1);

        scheduler.put(task1);
        scheduler.put(task2);
        scheduler.put(task3);
        scheduler.put(task4);
    }

//    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Hello epta!");
//
//        Random random = new Random();
//
//        TaskScheduler scheduler = new TaskScheduler(); // Создание планировщика
//        int id = 1;
//        while (true) {
//            int priority = random.nextInt(4);
//            scheduler.put(new BaseTask(priority, id++));
//
//            Thread.sleep(500 + random.nextInt(501));
//        }
//    }
}
