package 第7章类加载.classLoader;

/**
 * @program: JVMStudy
 * @description: ClassLoaderTest
 * @author: hjc
 * @create: 2019-02-16 21:22
 */
public class ClassLoaderTest extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class aClass = null;
        try {
            aClass = super.loadClass(name);
            return aClass;
        } catch (Exception e) {
            aClass = findClass(name);
        }
        return aClass;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}