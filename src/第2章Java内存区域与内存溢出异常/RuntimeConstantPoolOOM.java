package 第2章Java内存区域与内存溢出异常;

import java.util.ArrayList;
import java.util.List;

public class RuntimeConstantPoolOOM {
    /*
             限制方法区大小
    VM Args:-XX:PermSize=10M -XX:MaxPermSize=10M
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
