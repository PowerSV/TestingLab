package edu.spbstu.models;

public class ExtendedTask extends AbstractTask{
    public ExtendedTask(int id, int priority) {
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
