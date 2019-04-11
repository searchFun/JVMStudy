package 第12章Java内存模型与线程.concurrent.threadTest;

/**
 * @program: JVMStudy
 * @description: 有问题的挂起操作
 * @author: hjc
 * @create: 2019-02-23 17:30
 */
public class BadSuspend {
    public static Object u = new Object();

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                Thread.currentThread().suspend();
            }
        }
    }


    static ChangeObjectThread o1 = new ChangeObjectThread("t1");
    static ChangeObjectThread o2 = new ChangeObjectThread("t2");

    public static void main(String[] args) throws InterruptedException {
        o1.start();
        Thread.sleep(100);
        o2.start();
        o1.resume();
        o2.resume();
        o1.join();
        o2.join();
    }
}
