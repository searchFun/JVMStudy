package concurrent;

import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @Program: JVMStudy
 * @Description:
 * @Author: hjc
 * @CreateTime: 2019-08-01 10:14
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader();
        ClassLoader classLoader1 = ClassLoaderTest.class.getClassLoader();

        Class<?> hello = classLoader.loadClass("concurrent.Hello");
        Class<?> hello1 = classLoader1.loadClass("concurrent.Hello");

        System.out.println(classLoader.hashCode());
        System.out.println("classloader hashcode:"+hello.getClassLoader().hashCode());

        System.out.println(classLoader1);
        System.out.println("classloader1 hashcode:"+hello1.getClassLoader().hashCode());


        Method method = hello.getDeclaredMethod("getInstance",null);
        Method method1 = hello1.getDeclaredMethod("getInstance",null);

        Hello h= (Hello) method.invoke(null,null);
        Hello h1= (Hello) method1.invoke(null,null);

        System.out.println(h.hashCode());
        System.out.println(h1.hashCode());



    }
}

class MyClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name)  {
        try {
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream in = getClass().getResourceAsStream(fileName);
            if(in == null) {
                return super.loadClass(name);
            }
            byte[] b = new byte[in.available()];
            in.read(b);
            return defineClass(name,b,0,b.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Hello {
    private static Hello hello =new Hello();

    private Hello() {
    }
    public static Hello getInstance(){
        return hello;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
