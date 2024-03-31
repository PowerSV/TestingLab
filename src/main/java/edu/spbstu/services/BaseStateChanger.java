package edu.spbstu.services;

import edu.spbstu.models.AbstractTask;

public interface BaseStateChanger extends WaitStateChanger {
    void putInReadyState(AbstractTask task);
    void terminate(AbstractTask task);
}
