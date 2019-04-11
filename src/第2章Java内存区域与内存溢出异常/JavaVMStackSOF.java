package 第2章Java内存区域与内存溢出异常;

public class JavaVMStackSOF {
    /*          栈容量
    VM Args:   -Xss256k
    设置虚拟机栈（每个线程独有）和本地方法区        HotSpot没有本地方法区



     */


    /*
    设置了栈帧的大小为256k
    结果stack length: 1883

    */
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }


    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
