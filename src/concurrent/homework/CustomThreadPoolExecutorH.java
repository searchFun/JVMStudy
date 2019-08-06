package concurrent.homework;

import com.spdb.training.log.ILog;
import com.spdb.training.log.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: hjc
 * @time: 2019-08-06 15:07
 */
public class CustomThreadPoolExecutorH {

    private static ILog logger = LoggerFactory.getLogger(CustomThreadPoolExecutorH.class);
    /**
     * 线程池核心数
     */
    private static final int COOL_THREAD_NUMBER = 10;

    /**
     * 线程池线程最大数
     */
    private static final int MAX_THREAD_NUMBER = 50;

    /**
     * 队列最大深度
     */
    private static final int MAX_QUEUE_DEPTH = 50;


    /**
     * 线程工厂
     */
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = "TestThread-" + count.addAndGet(1);
            t.setName(threadName);
            return t;
        }
    };

    /**
     * 拒绝策略
     */
    static class CustomPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //记录异常
            System.err.println("。。。。。任务满，不能处理该任务请求。。。。。");
            r.run();
        }
    }


    public static ExecutorService newCustomThreadPool() {
        return new ThreadPoolExecutor(
                COOL_THREAD_NUMBER,
                MAX_THREAD_NUMBER,
                0L,
                TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(MAX_QUEUE_DEPTH),
                threadFactory,
                new CustomPolicy());
    }


    public static void main(String[] args) {
        Thread thread=Thread.currentThread();
        ExecutorService executorService = CustomThreadPoolExecutorH.newCustomThreadPool();
        for (int i = 1; i < 150; i++) {
            System.out.println("提交第" + i + "个任务");
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000*2);
                } catch (InterruptedException e) {
                    logger.error("ERROR",e);
                }
                if(Thread.currentThread().equals(thread)){
                    logger.debug(Thread.currentThread().getName()+"拒绝执行");
                }
                else {
                    logger.info(Thread.currentThread().getName()+"子线程执行");
                }
            });
        }
    }
}
