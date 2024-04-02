package edu.spbstu.transporters;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.ExtendedTask;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class WaitingTransporter extends AbstractTransporter {
    public WaitingTransporter(BlockingQueue<AbstractTask> producer, Consumer<AbstractTask> consumer) {
        super(producer, consumer);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ExtendedTask task = (ExtendedTask) producer.take();
                while (!task.isReady()) {
                    Thread.yield();
                }
                consumer.accept(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
