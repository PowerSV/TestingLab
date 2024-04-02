package edu.spbstu.models;

import edu.spbstu.services.Waitable;

public class ExtendedTask extends AbstractTask {

    private final Waitable waitStateProducer;
    private final long limit = RANDOM.nextInt(1000) + 50000;
    private long result;
    private int index;
    private boolean isWaiting = false;
    private long interruptTime;
    private long waitingTime;

    public ExtendedTask(int priority, Waitable waitStateProducer) {
        super(priority);
        setRunnable(getExtendedTask());
        this.waitStateProducer = waitStateProducer;
    }

    public boolean isReady() {
        if (!isWaiting) {
            return true;
        }

        long diff = System.currentTimeMillis() - interruptTime;
        if (diff >= waitingTime) {
            isWaiting = false;
            return true;
        }

        return false;
    }

    private Runnable getExtendedTask() {
        return () -> {
            LOGGER.info("Continue: " + index);
            while (index < limit) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                if (isWaitAction()) {
                    LOGGER.info("Waiting: " + index);

                    isWaiting = true;
                    waitingTime = getWaitingTime();
                    interruptTime = System.currentTimeMillis();

                    try {
                        waitStateProducer.putInWaitState(this);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                result += index;
                index++;
            }

            LOGGER.info(this + " end with result = " + result);
        };
    }

    private static boolean isWaitAction() { // With 0,001% probability.
//        int rand = RANDOM.nextInt(1000000);
        int rand = RANDOM.nextInt(1_000_000);
        return rand > 999_990;
    }

    private static int getWaitingTime() {
        // From 2000 to 4000 millis.
        return RANDOM.nextInt(2000) + 2000;
    }
}
