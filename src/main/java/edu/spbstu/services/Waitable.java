package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;

public interface Waitable {
    void putInWaitState(AbstractTask task) throws InterruptedException;
}
