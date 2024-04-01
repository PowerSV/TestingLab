package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;

public interface TaskManager {
    void putInRunningState(AbstractTask task);

    void putInReadyStateBlocking(AbstractTask task);

    void putInReadyStateNonBlocking(AbstractTask task);
}
