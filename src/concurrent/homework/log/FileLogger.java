package concurrent.homework.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author: hjc
 * @time: 2019-08-05 23:12
 */
public class FileLogger extends AbstractLogger {


    private final String LOG_HOME = "logs_";

    private final String DEBUG_FILE = LOG_HOME + "debug.log";
    private final String INFO_FILE = LOG_HOME + "info.log";
    private final String WARN_FILE = LOG_HOME + "warn.log";
    private final String ERROR_FILE = LOG_HOME + "error.log";

    public FileLogger(Class<?> clazz) throws Exception {
        super(clazz);
    }

    @Override
    public PrintStream setDebugWritter() {
        try {
            File file = new File(DEBUG_FILE);
            return new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PrintStream setInfoWritter() {
        try {
            File file = new File(INFO_FILE);
            return new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PrintStream setWarnWritter() {
        try {
            File file = new File(WARN_FILE);
            return new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PrintStream setErroWritter() {
        try {
            File file = new File(ERROR_FILE);
            return new PrintStream(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
