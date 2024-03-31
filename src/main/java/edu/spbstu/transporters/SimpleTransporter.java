package edu.spbstu.transporters;

import edu.spbstu.models.AbstractTask;

import java.util.concurrent.BlockingQueue;

public class SimpleTransporter extends Transporter{
    public SimpleTransporter(BlockingQueue<AbstractTask> producer, BlockingQueue<AbstractTask> consumer) {
        super(producer, consumer);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                consumer.put(producer.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
