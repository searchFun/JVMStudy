package concurrent.homework;

import java.io.*;
import java.util.Random;

/**
 * @author: hjc
 * @time: 2019-08-06 16:31
 */
public class FileMaker {

    /**
     * �ļ���
     */
    private static final String FILE_NAME = "number.txt";

    /**
     * ����
     */
    private static final int FILE_LINE_NUMBER = 1000000;

    /**
     * �����
     */
    private Random random;

    public FileMaker() {
        this.random = new Random();
    }

    /**
     * ���������
     *
     * @param -->
     * @return: double -->
     * @time: 2019/8/6 16:41
     */
    private Double randomNumber() {
        double min = 1;
        double max = 1000000;
        return formatDouble1(random.nextFloat() *(max-min));
    }

    /**
     * ������λС��
     * @param d -->
     * @return: double -->
     * @time: 2019/8/6 17:03
    */
    public double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }

    public static void main(String[] args) throws IOException {

        FileMaker fileMaker = new FileMaker();
        File file = new File(FILE_NAME);
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            for (int i = 0; i < FILE_LINE_NUMBER; i++) {
                writer.write(fileMaker.randomNumber().toString()+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
