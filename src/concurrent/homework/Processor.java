package concurrent.homework;

/**
 * 进度
 *
 * @author: hjc
 * @time: 2019-08-06 17:06
 */
public class Processor {
    /**
     * 进度条最低
     */
    private static final int MIN_PROCESS = 0;

    /**
     * 进度条最大
     */
    private static final int MAX_PROCESS = 100;


    /**
     * 可计数最小值
     */
    private int minProcess;

    /**
     * 可计数最大值
     */
    private int maxProcess;

    /**
     * 当前进度
     */
    private int nowProcess;


    public Processor(int minProcess, int maxProcess) {
        this.minProcess = minProcess;
        this.maxProcess = maxProcess;
        this.nowProcess = 0;
    }

//    public void printProcess
}
