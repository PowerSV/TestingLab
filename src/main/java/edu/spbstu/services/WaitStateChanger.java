package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;

public interface WaitStateChanger {
    void putInWaitState(AbstractTask task);
}
