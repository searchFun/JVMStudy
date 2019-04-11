package 第7章类加载.staticTest;

/**
 * @program: JVMStudy
 * @description:
 * @author: hjc
 * @create: 2019-02-21 22:07
 */
public class FirstSon extends Ancestor {
    public static void main(String[] args) {
        new FirstSon();
        a++;
        System.out.println(Ancestor.a);
        System.out.println(a);
        a++;
        System.out.println(Ancestor.a);
        System.out.println(FirstSon.a);
    }
}
