package concurrent.homework;

/**
 * ����
 *
 * @author: hjc
 * @time: 2019-08-06 17:06
 */
public class Processor {
    /**
     * ���������
     */
    private static final int MIN_PROCESS = 0;

    /**
     * ���������
     */
    private static final int MAX_PROCESS = 100;


    /**
     * �ɼ�����Сֵ
     */
    private int minProcess;

    /**
     * �ɼ������ֵ
     */
    private int maxProcess;

    /**
     * ��ǰ����
     */
    private int nowProcess;


    public Processor(int minProcess, int maxProcess) {
        this.minProcess = minProcess;
        this.maxProcess = maxProcess;
        this.nowProcess = 0;
    }

//    public void printProcess
}
