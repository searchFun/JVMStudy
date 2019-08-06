package concurrent.homework.log;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ����־ʵ��
 * ֱ��ʹ��System.out���
 *
 * @author wanglw2
 */
public abstract class AbstractLogger implements ILog, LogWritter {
    /**
     * ��־����
     */
    static int logLevel = 0;

    static {
        String logL = System.getProperty("LOGLEVEL", "DEBUG");
        logLevel = LogEnum.getLevelByName(logL);
    }


    /**
     * ʹ����־��¼��class
     */
    Class<?> clazz;


    /**
     * info��Ϣ�����
     */
    PrintStream infoOutputStream;

    /**
     * debug��Ϣ�����
     */
    PrintStream debugOutputStream;

    /**
     * warn��Ϣ�����
     */
    PrintStream warnOutputStream;

    /**
     * error��Ϣ�����
     */
    PrintStream errorOutputStream;


    public AbstractLogger(Class<?> clazz) {
        this.clazz = clazz;
        setOutPutStream();
    }

    /**
     * ���������
     *
     * @param -->
     * @return: void -->
     * @time: 2019/8/5 23:17
     */
    private void setOutPutStream() {
        infoOutputStream = setInfoWritter();
        debugOutputStream = setDebugWritter();
        warnOutputStream = setWarnWritter();
        errorOutputStream = setErroWritter();
    }

    /**
     * ��־�ı�
     *
     * @param logType
     * @param msg
     * @return
     */
    private String getLogText(LogEnum logType, String msg) {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss");
        StringBuffer logSB = new StringBuffer();
        logSB.append(dFormat.format(new Date())+" ");
        logSB.append(" | ");
        logSB.append(Thread.currentThread().getName());
        logSB.append(" | ");
        logSB.append(logType.getPreLogText());
        logSB.append(" | ");
        logSB.append((clazz == null ? "" : clazz.getName()));
        logSB.append(" | ");
        logSB.append(msg);
        return logSB.toString();
    }


    /**
     * ���ݸ�ʽ��Ϣ��ȡ������Ϣ����
     * ���磺getMsg("֤���ţ�{} ������ ","001002") -> "֤���ţ�001002 ������"
     *
     * @param msg
     * @param args
     * @return
     */
    private String getMsg(String msg, Object... args) {
        for (Object object : args) {
            msg = msg.replaceFirst("\\{\\}", object == null ? "null" : object.toString());
        }
        return msg;
    }

    @Override
    public void info(String msg) {
        if (!showLog(LogEnum.INFO)) {
            return;
        }
        String logT = getLogText(LogEnum.INFO, msg);
        infoOutputStream.println(logT);
    }


    @Override
    public void info(String msg, Throwable t) {
        if (!showLog(LogEnum.INFO)) {
            return;
        }
        String logT = getLogText(LogEnum.INFO, msg);
        infoOutputStream.println(logT);
        t.printStackTrace(errorOutputStream);
    }

    @Override
    public void info(String msg, Object... args) {
        if (!showLog(LogEnum.INFO)) {
            return;
        }
        String logT = getLogText(LogEnum.INFO, getMsg(msg, args));
        infoOutputStream.println(logT);
    }

    @Override
    public void warn(String msg) {
        if (!showLog(LogEnum.WARN)) {
            return;
        }
        String logT = getLogText(LogEnum.WARN, msg);
        warnOutputStream.println(logT);
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (!showLog(LogEnum.WARN)) {
            return;
        }
        String logT = getLogText(LogEnum.WARN, msg);
        warnOutputStream.println(logT);
        t.printStackTrace(errorOutputStream);
    }

    @Override
    public void warn(String msg, Object... args) {
        if (!showLog(LogEnum.WARN)) {
            return;
        }
        String logT = getLogText(LogEnum.WARN, getMsg(msg, args));
        warnOutputStream.println(logT);
    }

    @Override
    public void error(String msg) {
        if (!showLog(LogEnum.ERROR)) {
            return;
        }
        String logT = getLogText(LogEnum.ERROR, msg);
        errorOutputStream.println(logT);
    }

    @Override
    public void error(String msg, Throwable t) {
        if (!showLog(LogEnum.ERROR)) {
            return;
        }
        String logT = getLogText(LogEnum.ERROR, msg);
        errorOutputStream.println(logT);
        t.printStackTrace(errorOutputStream);
    }

    @Override
    public void error(String msg, Object... args) {
        if (!showLog(LogEnum.ERROR)) {
            return;
        }
        String logT = getLogText(LogEnum.ERROR, getMsg(msg, args));
        errorOutputStream.println(logT);
    }

    @Override
    public void debug(String msg) {
        if (!showLog(LogEnum.DEBUG)) {
            return;
        }
        String logT = getLogText(LogEnum.DEBUG, msg);
        debugOutputStream.println(logT);
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (!showLog(LogEnum.DEBUG)) {
            return;
        }
        String logT = getLogText(LogEnum.DEBUG, msg);
        debugOutputStream.println(logT);
        t.printStackTrace(errorOutputStream);
    }

    @Override
    public void debug(String msg, Object... args) {
        if (!showLog(LogEnum.DEBUG)) {
            return;
        }
        String logT = getLogText(LogEnum.DEBUG, getMsg(msg, args));
        debugOutputStream.println(logT);
    }

    private boolean showLog(LogEnum logEnum) {
        return logEnum.getLogLevel() >= logLevel;
    }

}
