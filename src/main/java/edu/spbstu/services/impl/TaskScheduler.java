package edu.spbstu.services.impl;

import edu.spbstu.MultiPriorityQueue;
import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.BaseTask;
import edu.spbstu.services.BaseStateChanger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class TaskScheduler implements BaseStateChanger {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskScheduler.class);
    private static final Comparator<AbstractTask> COMPARATOR = Comparator.comparing(AbstractTask::getPriority);

    private final MultiPriorityQueue readyQueue = new MultiPriorityQueue();
    private final PriorityBlockingQueue<AbstractTask> waitingQueue
            = new PriorityBlockingQueue<>(64, COMPARATOR);
    private final PriorityBlockingQueue<AbstractTask> suspendedQueue
            = new PriorityBlockingQueue<>(64, COMPARATOR);

    @Override
    public void putInReadyState(AbstractTask task) {

    }

    @Override
    public void terminate(AbstractTask task) {
        LOGGER.info("Task done: id=" + task.getId());
    }

    @Override
    public void putInWaitState(AbstractTask task) {
        readyQueue.put(task);
    }
}
