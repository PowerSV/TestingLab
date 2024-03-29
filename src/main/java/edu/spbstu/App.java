package edu.spbstu;

import edu.spbstu.services.TaskGenerator;
import edu.spbstu.services.TaskScheduler;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        int numPriorities = 4; // Количество уровней приоритета задач

        TaskScheduler scheduler = new TaskScheduler(numPriorities); // Создание планировщика
        TaskGenerator taskGenerator = new TaskGenerator(scheduler); // Создание генератора задач

        // Создание и запуск потоков
        Thread generatorThread = new Thread(taskGenerator);
        Thread schedulerThread = new Thread(scheduler);

        generatorThread.start();
        schedulerThread.start();
    }
}
