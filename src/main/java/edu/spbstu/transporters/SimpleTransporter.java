package edu.spbstu.transporters;

import edu.spbstu.models.AbstractTask;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class SimpleTransporter extends AbstractTransporter {
    public SimpleTransporter(BlockingQueue<AbstractTask> producer, Consumer<AbstractTask> consumer) {
        super(producer, consumer);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                consumer.accept(producer.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
