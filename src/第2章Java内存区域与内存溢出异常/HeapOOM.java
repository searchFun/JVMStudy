package 第2章Java内存区域与内存溢出异常;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
    /*
      OutOfMemoryError:

              堆的最小值  堆的最大值   虚拟机出现内存溢出异常是Dump出当前的内存堆转储为快照
      VM Args:-Xms20m   -Xmx20m    -XX:+HeapDumpOnOutOfMemoryError
       */

    static class OOMObject {

    }

    public static void main(String[] args) {
       //爆破堆  （实例对象）
        List<OOMObject> list = new ArrayList<>();
        while (true) {
           try{
               list.add(new OOMObject());
               System.out.println(list.size());
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }
}
