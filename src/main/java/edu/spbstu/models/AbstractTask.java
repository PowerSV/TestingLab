package edu.spbstu.models;

public abstract class AbstractTask {
    protected final int id;
    protected int priority;
    protected TaskState state;

    public AbstractTask(int id, int priority) {
        this.id = id;
        this.priority = priority;
        this.state = TaskState.SUSPENDED;
    }

    public int getPriority() {
        return priority;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public abstract TaskState activate();

    public abstract TaskState start();

    public abstract TaskState preempt();

    public abstract TaskState terminate();

    public abstract void executeTask();
}
