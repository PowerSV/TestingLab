package edu.spbstu;

import edu.spbstu.models.BaseTask;
import edu.spbstu.services.impl.TaskScheduler;

import java.util.Random;

public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello epta!");

        Random random = new Random();
        int numPriorities = 4; // Количество уровней приоритета задач

        TaskScheduler scheduler = new TaskScheduler(numPriorities); // Создание планировщика
        int id = 0;
        while (true) {
            int priority = random.nextInt(numPriorities);
            scheduler.addTask(new BaseTask(priority, id++));

            Thread.sleep(500 + random.nextInt(501));
        }
    }
}
