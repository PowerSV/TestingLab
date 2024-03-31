package edu.spbstu.models;

public class BaseTask extends AbstractTask {
    public BaseTask(int id, int priority) {
        super(id, priority);
        setRunnable(getBaseTask());
    }

    public Runnable getBaseTask() {
        return () -> {
            long counter = 0;
            long limit = 1_000_000 + RANDOM.nextInt(2_000_000);
            for (int i = 0; i < limit; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                counter++;
            }
            LOGGER.info(this + " end with result = " + counter);
        };
    }
}
