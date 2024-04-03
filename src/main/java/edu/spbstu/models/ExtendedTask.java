package edu.spbstu.models;

import edu.spbstu.services.Waitable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class ExtendedTask extends AbstractTask {

    private final Waitable waitStateProducer;
    private final long limit = RANDOM.nextInt(1000) + 50000;
    private long result;
    private int index;
    private long waitingTime;
    private CountDownLatch readyLatch;

    public ExtendedTask(int priority, Waitable waitStateProducer) {
        super(priority);
        setRunnable(getExtendedTask());
        this.waitStateProducer = waitStateProducer;
    }

    public void awaitReady() throws InterruptedException {
        readyLatch.await();
    }

    private Runnable getExtendedTask() {
        return () -> {
            if (index > 0) {
                LOGGER.info("Continue: " + index);
            }
            while (index < limit) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                if (isWaitAction()) {
                    LOGGER.info("Waiting: " + index);

                    waitingTime = getWaitingTime();
                    readyLatch = new CountDownLatch(1);

                    try {
                        waitStateProducer.putInWaitState(this);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Executors.newSingleThreadExecutor().submit(() -> {
                        try {
                            Thread.sleep(waitingTime);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        readyLatch.countDown();
                    });
                    return;
                }

                result += index;
                index++;
            }

            LOGGER.info(this + " end with result = " + result);
        };
    }

    private static boolean isWaitAction() { // With 0,001% probability.
        int rand = RANDOM.nextInt(1_000_000);
        return rand > 999_990;
    }

    private static int getWaitingTime() {
        // From 2000 to 4000 millis.
        return RANDOM.nextInt(2000) + 2000;
    }
}
