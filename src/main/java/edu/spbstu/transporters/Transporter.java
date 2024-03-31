package edu.spbstu.transporters;

import edu.spbstu.models.AbstractTask;

import java.util.concurrent.BlockingQueue;

public abstract class Transporter extends Thread {
    BlockingQueue<AbstractTask> producer;
    BlockingQueue<AbstractTask> consumer;

    public Transporter(BlockingQueue<AbstractTask> producer, BlockingQueue<AbstractTask> consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }
}
