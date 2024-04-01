package edu.spbstu.transporters;

import edu.spbstu.models.AbstractTask;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReadyToRunningTransporter extends Thread {
    Supplier<AbstractTask> producer;
    Consumer<AbstractTask> consumer;

    public ReadyToRunningTransporter(Supplier<AbstractTask> producer, Consumer<AbstractTask> consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            consumer.accept(producer.get());
        }
    }
}
