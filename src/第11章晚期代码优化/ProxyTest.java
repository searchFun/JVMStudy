package 第11章晚期代码优化;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: JVMStudy
 * @description: 代理类测试
 * @author: hjc
 * @create: 2019-02-17 14:05
 */
public class ProxyTest {
    interface IHello {
        void sayHello();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler {
        Object originalObj;

        Object bind(Object originalObj) {
            this.originalObj = originalObj;
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object object=method.invoke(originalObj, args);
            System.out.println("welcome");
            return object;
        }

        public static void main(String[] args) {
            System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
            IHello hello = (IHello) new DynamicProxy().bind(new Hello());
            hello.sayHello();
        }
    }
}
