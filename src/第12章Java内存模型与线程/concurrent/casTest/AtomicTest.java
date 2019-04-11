package 第12章Java内存模型与线程.concurrent.casTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: JVMStudy
 * @description: 测试CAS
 * @author: hjc
 * @create: 2019-02-24 22:23
 */
public class AtomicTest {
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static volatile boolean isOver = false;

    public static void main(String[] args) {
        Runnable runnable = () -> {
            isOver = false;
            atomicInteger.incrementAndGet();
            isOver = true;
        };
        Thread thread = new Thread(runnable);
        for (int i = 0; i < 100; i++) {
            thread.start();
            while (isOver == false) {
            }
            System.out.println(atomicInteger.get());
        }
    }
}
