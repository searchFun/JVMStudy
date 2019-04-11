package 第12章Java内存模型与线程.HotSwap;

import java.lang.reflect.Method;

/**
 * @program: JVMStudy
 * @description: JavaClass执行工具
 * @author: hjc
 * @create: 2019-02-17 21:33
 */
public class JavaClassExecuter {
    public static String execute(byte[] classBytes) {
        HackSystem.clearBuffer();

        ClassModifier cm = new ClassModifier(classBytes);

        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "第12章Java内存模型与线程/HotSwap/HackSystem");

        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);
        try {
            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (Throwable e) {
            e.printStackTrace(HackSystem.err);
        }
        return HackSystem.getBufferString();
    }
}
