package 第12章Java内存模型与线程.HotSwap;

/**
 * @program: JVMStudy
 * @description: byte数组处理工具
 * @author: hjc
 * @create: 2019-02-17 20:35
 */
public class ByteUtils {
    public static int byte2Int(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = (int) b[i] & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

    public static byte[] int2Bytes(int value, int len) {
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (value >> (len - i - 1) * 8);
        }
        return bytes;
    }

    public static String bytes2Stirng(byte[] bytes, int start, int len) {
        return new String(bytes, start, len);
    }

    public static byte[] string2Bytes(String str) {
        return str.getBytes();
    }

    public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes) {
        byte[] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
        //拷贝偏移前
        System.arraycopy(originalBytes, 0, newBytes, 0, offset);
        //替换
        System.arraycopy(originalBytes, offset, newBytes, offset, replaceBytes.length);
        //拷贝便宜后
        System.arraycopy(originalBytes, offset + len, newBytes, offset + replaceBytes.length, originalBytes.length - offset - len);
        return newBytes;
    }
}
