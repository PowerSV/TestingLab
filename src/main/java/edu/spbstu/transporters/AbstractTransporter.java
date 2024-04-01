package edu.spbstu.transporters;

import edu.spbstu.models.AbstractTask;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public abstract class AbstractTransporter extends Thread {
    BlockingQueue<AbstractTask> producer;
    Consumer<AbstractTask> consumer;

    public AbstractTransporter(BlockingQueue<AbstractTask> producer, Consumer<AbstractTask> consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }
}
