package concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Program: JVMStudy
 * @Description: CountDownLatch测试
 * @Author: hjc
 * @CreateTime: 2019-07-19 10:52
 */
public class CountDownLatchTest {
    static final int SIZE = 100;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        for (int i = 0; i < 10; i++) {
            service.execute(new WaitingTask(countDownLatch));
        }

        for (int i = 0; i < SIZE; i++) {
            service.execute(new TaskPortion(countDownLatch));
        }

        System.out.println("Launch all tasks");
        service.shutdown();
    }
}


class TaskPortion implements Runnable {

    private static int counter = 0;

    private final int id = counter++;

    private static Random random = new Random(47);

    private final CountDownLatch latch;


    public TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }


    @Override
    public void run() {
        try {
            doWork();
        } catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        } finally {
            latch.countDown();
        }
    }

    private void doWork() throws InterruptedException {
        long sleepTime = random.nextInt(2000);
        System.out.println(this+"prepare sleep "+ sleepTime + " s");
        TimeUnit.MILLISECONDS.sleep(sleepTime);
        System.out.println(this + "complete");
    }

    @Override
    public String toString() {
        return String.format("%1$-3d ", id);
    }
}


class WaitingTask implements Runnable {

    private static int count = 0;
    private final int id = count++;

    private final CountDownLatch latch;

    public WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

