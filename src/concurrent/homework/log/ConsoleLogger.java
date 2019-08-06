package concurrent.homework.log;

import java.io.PrintStream;

/**
 * @author: hjc
 * @time: 2019-08-05 23:12
 */
public class ConsoleLogger extends AbstractLogger {


    public ConsoleLogger(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public PrintStream setDebugWritter() { return System.out; }

    @Override
    public PrintStream setInfoWritter() {
        return System.out;
    }

    @Override
    public PrintStream setWarnWritter() {
        return System.out;
    }

    @Override
    public PrintStream setErroWritter() {
        return System.out;
    }
}
