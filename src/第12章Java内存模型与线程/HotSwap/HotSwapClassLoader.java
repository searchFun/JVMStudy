package 第12章Java内存模型与线程.HotSwap;

/**
 * @program: JVMStudy
 * @description: 热交换 加载器
 * @author: hjc
 * @create: 2019-02-17 20:15
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte,0, classByte.length);
    }
}
