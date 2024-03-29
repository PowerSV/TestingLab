package edu.spbstu.models;

public class BaseTask extends AbstractTask {
    public BaseTask(int id, int priority) {
        super(id, priority);
    }

    @Override
    public TaskState activate() {
        return null;
    }

    @Override
    public TaskState start() {
        return null;
    }

    @Override
    public TaskState preempt() {
        return null;
    }

    @Override
    public TaskState terminate() {
        return null;
    }

    @Override
    public void executeTask() {
        // Логика выполнения задачи
        long sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum++;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Task " + id + " with priority " + priority + " executed with sum: " + sum);
    }
}
