package 第12章Java内存模型与线程;

import java.util.Map;

/**
 * @program: JVMStudy
 * @description: volatile测试
 * @author: hjc
 * @create: 2019-02-18 12:59
 */
public class VolatileTest {
    /*
    volatile：保证了所有线程可见性 但是并不是线程安全的   使用： 修饰状态值  一个地方改变了  所有线程均可知  这个变量不依赖原值
     */
    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }
            });
            threads[i].start();

            while (Thread.activeCount() > 1) {
                Thread.yield();
                System.out.println(race);
            }
        }
    }

    //better            start
    private volatile boolean shutdown = false;

    public void shutDown() {
        while (!shutdown) {
            //do somethings
        }
    }
    //better            end



    /*
    禁止指令重排序优化
     */
    Map configOptions;
    char[] configTest;
    volatile boolean initialized = false;

}
