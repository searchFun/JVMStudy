package concurrent.homework.log;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志工厂
 *
 * @author: hjc
 * @time: 2019-08-05 21:50
 */
public class LoggerFactory implements ILog {


    private LoggerFactory() {}

    private final static Class[] iLogClasses = new Class[]{ ConsoleLogger.class};

    private List<ILog> iLogList;

    public static LoggerFactory getLogger(Class<?> clazz) {
        LoggerFactory loggerFactory = new LoggerFactory();
        loggerFactory.iLogList = new ArrayList<>();

        for (Class logClass : iLogClasses) {
            ILog iLog = null;
            try {
                iLog = (ILog) logClass.getDeclaredConstructor(Class.class).newInstance(clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
            loggerFactory.iLogList.add(iLog);
        }
        return loggerFactory;
    }

    private Class<?> clazz;

    @Override
    public void debug(String msg) {
        iLogList.forEach(log -> log.debug(msg));
    }

    @Override
    public void debug(String msg, Throwable t) {
        iLogList.forEach(log -> log.debug(msg, t));
    }

    @Override
    public void debug(String msg, Object... args) {
        iLogList.forEach(log -> log.debug(msg, args));
    }

    @Override
    public void info(String msg) {
        iLogList.forEach(log -> log.info(msg));
    }

    @Override
    public void info(String msg, Throwable t) {
        iLogList.forEach(log -> log.info(msg, t));
    }

    @Override
    public void info(String msg, Object... args) {
        iLogList.forEach(log -> log.info(msg, args));
    }

    @Override
    public void warn(String msg) {
        iLogList.forEach(log -> log.warn(msg));
    }

    @Override
    public void warn(String msg, Throwable t) {
        iLogList.forEach(log -> log.warn(msg, t));
    }

    @Override
    public void warn(String msg, Object... args) {
        iLogList.forEach(log -> log.warn(msg, args));
    }

    @Override
    public void error(String msg) {
        iLogList.forEach(log -> log.error(msg));
    }

    @Override
    public void error(String msg, Throwable t) {
        iLogList.forEach(log -> log.error(msg, t));
    }

    @Override
    public void error(String msg, Object... args) {
        iLogList.forEach(log -> log.error(msg, args));
    }

}
