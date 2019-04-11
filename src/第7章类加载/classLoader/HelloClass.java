package 第7章类加载.classLoader;

/**
 * @program: JVMStudy
 * @description: Class文件属性查看
 * @author: hjc
 * @create: 2019-01-15 22:32
 */
public class HelloClass {

    static char c = 'c';

    public void sayHello(){
        System.out.println("hello world!");
    }

    public static void main(String[] args) {
        HelloClass helloClass=new HelloClass();
        helloClass.sayHello();
    }
}
