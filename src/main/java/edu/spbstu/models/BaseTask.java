package edu.spbstu.models;

public class BaseTask extends AbstractTask{
    public BaseTask(String name, int priority, TaskState state) {
        super(name, priority, state);
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
}
