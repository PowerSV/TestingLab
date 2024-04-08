package edu.spbstu;

import edu.spbstu.models.ExtendedTask;
import edu.spbstu.services.impl.TaskScheduler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtendedTaskTest {

    @Test
    public void testExtendedTaskExecution() {
        TaskScheduler taskScheduler = new TaskScheduler(4);
        ExtendedTask task = new ExtendedTask(3, taskScheduler);

        Thread thread = new Thread(task.getExtendedTask());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertFalse(thread.isAlive());
    }

    @Test
    public void testExtendedTaskInterruption()  {
        TaskScheduler taskScheduler = new TaskScheduler(4);
        ExtendedTask task = new ExtendedTask(3, taskScheduler);

        Thread thread = new Thread(task.getExtendedTask());
        thread.start();
        thread.interrupt();

        assertTrue(thread.isAlive());
    }
}
