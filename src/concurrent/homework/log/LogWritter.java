/**
 * @fileName:LogWritter.java
 * @descprition: TODO
 * @author: hjc
 * @date: 2019年8月5日
 */
package concurrent.homework.log;

import java.io.PrintStream;

/**
 * @author hjc
 *
 */
public interface LogWritter {

    /**
     * 设置debug 输出流
     * @param  -->
     * @return: java.io.PrintStream -->
     * @time: 2019/8/5 23:50
    */
    public PrintStream setDebugWritter() ;

    /**
     * 设置info 输出流
     * @param  -->
     * @return: java.io.PrintStream -->
     * @time: 2019/8/5 23:50
     */
    public PrintStream setInfoWritter();

    /**
     * 设置warn 输出流
     * @param  -->
     * @return: java.io.PrintStream -->
     * @time: 2019/8/5 23:50
     */
    public PrintStream setWarnWritter() ;

    /**
     * 设置error 输出流
     * @param  -->
     * @return: java.io.PrintStream -->
     * @time: 2019/8/5 23:50
     */
    public PrintStream setErroWritter() ;

}
