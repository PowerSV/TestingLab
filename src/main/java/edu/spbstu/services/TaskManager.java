package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;

public interface TaskManager extends Waitable {
    void putInReadyStateBlocking(AbstractTask task);

    void putInReadyStateNonBlocking(AbstractTask task);

    AbstractTask peekFromReadyState();

    AbstractTask takeFromReadyState();
}
