package 第10章早期代码优化;

import java.util.Scanner;

/**
 * @program: JVMStudy
 * @description: 自动装箱   语法糖
 * @author: hjc
 * @create: 2019-01-25 00:04
 */
public class AutoGoGoGo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String sum = "1";
        for (int i = 1; i <= n; i++) {
            sum = sxs(sum, i);
        }
        System.out.println(sum);
    }

    public static String sxs(String a, int b) {
        //进位
        int more = 0;
        //字符串长度
        int len = a.length();
        //每一位的数据
        int c;
        StringBuilder sb = new StringBuilder();
        for (int i = len - 1; i >= 0; i--) {
            c = a.charAt(i) - '0';
            int result = c * b + more;
            more = result / 10;
            sb.insert(0,result%10);
        }
        if(more!=0){
            sb.insert(0,more);
        }
        return sb.toString();
    }
}
