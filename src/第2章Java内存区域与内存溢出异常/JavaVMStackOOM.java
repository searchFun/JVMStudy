package 第2章Java内存区域与内存溢出异常;

public class JavaVMStackOOM {
    /*
    创建线程导致内存溢出   OutOfMemoryError
    VM Args:-Xss2M
     */
    private int threadLength=0;
    private void dontStop() {
        while (true) {

        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(() -> {
                threadLength++;
                dontStop();
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        try {
            oom.stackLeakByThread();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.threadLength);
            throw e;
        }
    }
}
