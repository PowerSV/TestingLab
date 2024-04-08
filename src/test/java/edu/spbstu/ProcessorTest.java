package edu.spbstu;

import edu.spbstu.models.AbstractTask;
import edu.spbstu.models.BaseTask;
import edu.spbstu.services.impl.TaskScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ProcessorTest {

    @Test
    public void processorExecutionPriorityTest() throws InterruptedException {
        TaskScheduler taskScheduler = new TaskScheduler(4);
        AbstractTask task1 = new BaseTask(2);
        AbstractTask task2 = new BaseTask(3);
        taskScheduler.put(task1);
        TimeUnit.MILLISECONDS.sleep(300);
        taskScheduler.put(task2);
        Assertions.assertTrue(taskScheduler.getProcessor().isAlive());
        TimeUnit.MILLISECONDS.sleep(30);
        Assertions.assertEquals(task2, taskScheduler.getProcessor().getTaskInProcess().getTask());
    }
}
