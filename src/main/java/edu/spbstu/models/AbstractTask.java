package edu.spbstu.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public abstract class AbstractTask implements Runnable {
    static final Logger LOGGER = LoggerFactory.getLogger(AbstractTask.class);
    static final Random RANDOM = new Random();
    private static int idGenerator = 1;

    protected final int id;
    protected int priority;

    private Runnable runnable = null;

    public AbstractTask(int priority) {
        this.id = idGenerator++;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        if (runnable == null) {
            throw new IllegalStateException();
        }
        runnable.run();
    }

    @Override
    public String toString() {
        return "AbstractTask{" +
                "id=" + id +
                ", priority=" + priority +
                '}';
    }
}
