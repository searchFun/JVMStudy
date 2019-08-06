package concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @Program: JVMStudy
 * @Description:
 * @Author: hjc
 * @CreateTime: 2019-07-31 16:10
 */
public class ForkJonPoolAction {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("系统核心："+Runtime.getRuntime().availableProcessors());
        PrintTask task = new PrintTask(0, 100);
        //创建实例，并执行分割任务
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);
        //线程阻塞，等待所有任务完成
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }
}

class PrintTask extends RecursiveAction {
    //最多只能打印50个数
    private static final int THRESHOLD = 50;
    private int start;
    private int end;



    PrintTask(int start, int end) {
        super();
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {

        if(end - start < THRESHOLD){
            for(int i=start;i<end;i++){
                System.out.println(Thread.currentThread().getName()+"的i值："+i);
            }
        }else {
            int middle =(start+end)/2;
            PrintTask left = new PrintTask(start, middle);
            PrintTask right = new PrintTask(middle, end);
            //并行执行两个“小任务”
            left.fork();
            right.fork();
        }

    }

}
