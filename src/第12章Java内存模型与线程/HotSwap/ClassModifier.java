package 第12章Java内存模型与线程.HotSwap;

/**
 * @program: JVMStudy
 * @description: 修改class文件
 * @author: hjc
 * @create: 2019-02-17 20:20
 */
public class ClassModifier {

    //Class 文件中常量池的起始偏移
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;
    //CONSTANT_Utf8_info tag标志
    private static final int CONSTANT_Utf8_info = 1;
    //常量池中11中常量所占的长度，CONSTANT_Utf8_info常量除外，因为它不是定长,前面3个没有
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5, -1, -1, 4, 3, 5};

    private static final int u1 = 1;
    private static final int u2 = 2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        //获取常量池数目
        int cpc = getConstantPoolCount();

        int offset = CONSTANT_POOL_COUNT_INDEX + u2;

        for (int i = 0; i < cpc; i++) {
            int tag = ByteUtils.byte2Int(classByte, offset, u1);
            if (tag == CONSTANT_Utf8_info) {
                int len = ByteUtils.byte2Int(classByte, offset + u1, u2);
                offset += (u1 + u2);
                String str = ByteUtils.bytes2Stirng(classByte, offset, len);
                if (str.equalsIgnoreCase(oldStr)) {
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
                    //替换长度
                    classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
                    //替换字符串字节
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
                    return classByte;
                } else {
                    //调到下一个
                    offset += len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classByte;
    }

    public int getConstantPoolCount() {
        return ByteUtils.byte2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}
