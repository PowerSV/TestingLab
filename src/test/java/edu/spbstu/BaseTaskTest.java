package edu.spbstu;

import edu.spbstu.models.BaseTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTaskTest {

    @Test
    public void testExtendedTaskExecution() {
        BaseTask task = new BaseTask(3);

        Thread thread = new Thread(task.getBaseTask());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertFalse(thread.isAlive());
    }

    @Test
    public void testExtendedTaskInterruption() throws InterruptedException {
        BaseTask task = new BaseTask(3);

        Thread thread = new Thread(task.getBaseTask());
        thread.start();
        thread.interrupt();

        assertTrue(thread.isAlive());
        thread.join();
    }
}
