package 第7章类加载.classLoader;

import java.util.Arrays;

/**
 * @program: JVMStudy
 * @description:
 * @author: hjc
 * @create: 2019-04-10 22:05
 */
public class BytesUtil {

    public static byte[] bytesAppend(byte[] original, byte[] append) {
        byte[] newBytes = new byte[original.length + append.length];
        System.arraycopy(original, 0, newBytes, 0, original.length);
        System.arraycopy(append, 0, newBytes, original.length, append.length);
        return newBytes;
    }

    public static int bytes2Int(byte[] bytes) {
        int sum = 0;
        for (int i = 0; i < bytes.length; i++) {
            sum = sum * 16 + Byte.toUnsignedInt(bytes[i]);
        }
        return sum;
    }

    public static String bytes2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toHexString(bytes[i]));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(Byte.toUnsignedInt((byte) 0xfc));
        System.out.println(bytes2HexString(new byte[]{(byte) 0xfc}));

    }
}
