package edu.spbstu;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.BaseTask;
import edu.spbstu.models.ExtendedTask;
import edu.spbstu.services.impl.TaskScheduler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskSchedulerTest {

    @Test
    public void testReadyTask() throws InterruptedException {
        TaskScheduler taskScheduler = new TaskScheduler(4);

        AbstractTask task = new BaseTask(0);
        taskScheduler.put(task);
        taskScheduler.getProcessor().interrupt();
        assertEquals(task, taskScheduler.peekFromReadyState());
    }

    @Test
    public void testPriority() throws InterruptedException {
        TaskScheduler taskScheduler = new TaskScheduler(4);

        AbstractTask task1 = new ExtendedTask(3, taskScheduler);
        AbstractTask task2 = new ExtendedTask(0, taskScheduler);
        taskScheduler.put(task2);
        taskScheduler.put(task1);
        taskScheduler.getProcessor().interrupt();
        assertEquals(3, task1.getPriority());
        assertEquals(0, task2.getPriority());
        assertEquals(task1, taskScheduler.peekFromReadyState());
        assertEquals(task1, taskScheduler.takeFromReadyState());
        assertEquals(task2, taskScheduler.peekFromReadyState());
    }

    @Test
    public void testTaskWait() throws InterruptedException {
        TaskScheduler taskScheduler = new TaskScheduler(4);
        ExtendedTask task = new ExtendedTask(3, taskScheduler);
        taskScheduler.putInWaitState(task);

        assertEquals(1, taskScheduler.getWaitingQueue().size());
    }

    @Test
    public void testTaskSuspended() throws InterruptedException {
        TaskScheduler taskScheduler = new TaskScheduler(4);
        ExtendedTask task = new ExtendedTask(3, taskScheduler);
        taskScheduler.put(task);

        if (!task.isWaiting()) {
            assertEquals(1, taskScheduler.getSuspendedQueue().size());
        } else {
            assertEquals(1, taskScheduler.getWaitingQueue().size());
        }
    }
}
