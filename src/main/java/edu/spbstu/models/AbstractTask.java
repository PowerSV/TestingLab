package edu.spbstu.models;

public abstract class AbstractTask implements Runnable{
    protected final String name;
    protected int priority;
    protected TaskState state;

    public AbstractTask(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.state = TaskState.SUSPENDED;
    }

    public String getName() {
        return name;
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

}
