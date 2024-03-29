package edu.spbstu.models;

public class ExtendedTask extends AbstractTask{
    public ExtendedTask(String name, int priority) {
        super(name, priority);
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

    public TaskState myWait() {
        return null;
    }

    public TaskState release() {
        return null;
    }

    @Override
    public void run() {

    }
}
