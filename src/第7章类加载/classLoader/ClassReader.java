package 第7章类加载.classLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: JVMStudy
 * @description:
 * @author: hjc
 * @create: 2019-04-10 21:54
 */
public class ClassReader {
    public static final int U1 = 1;
    public static final int U2 = 2;
    public static final int U3 = 3;
    public static final int U4 = 4;
    public static final int U8 = 8;


    public ClassReader(InputStream inputStream) {
        uByte.inputStream = inputStream;
    }

    //读取文件流类型
    static class uByte {
        private byte[] value;
        public int length;
        private static InputStream inputStream;

        public static void initInputStream(InputStream inputStream) {
            uByte.inputStream = inputStream;
        }

        public uByte(int length) {
            this.length = length;
            value = new byte[length];
            read();
        }

        private void read() {
            try {
                inputStream.read(value, 0, length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public byte[] getValue() {
            return value;
        }

        public void append(byte[] bytes) {
            value = BytesUtil.bytesAppend(value, bytes);
            length = value.length;
        }

        @Override
        public String toString() {
            return BytesUtil.bytes2HexString(value);
        }
    }


    //魔数                           U4              1
    private uByte magic;
    //次版本                         U2              1
    private uByte minor_version;
    //主版本                         U2              1
    private uByte major_version;
    //常量池计数   constant_pool_count-1           U2              1
    private uByte constan_pool_count;

    //----------------------------------Constant Pool  Start--------------
    static abstract class ConstantInfo {
        private static final int CONSTANT_Utf8_info_TAG = 1;
        private static final int CONSTANT_Interger_info_TAG = 3;
        private static final int CONSTANT_Float_info_TAG = 4;
        private static final int CONSTANT_Long_info_TAG = 5;
        private static final int CONSTANT_Double_info_TAG = 6;
        private static final int CONSTANT_Class_info_TAG = 7;
        private static final int CONSTANT_String_info_TAG = 8;
        private static final int CONSTANT_Fieldref_info_TAG = 9;
        private static final int CONSTANT_Methodref_info_TAG = 10;
        private static final int CONSTANT_InterfaceMethodref_info_TAG = 11;
        private static final int CONSTANT_NameAndType_info_TAG = 12;
        private static final int CONSTANT_MethodHandle_info_TAG = 15;
        private static final int CONSTANT_MethodType_info_TAG = 16;
        private static final int CONSTANT_InvokeDynamic_info_TAG = 18;


        //类型            1
        public uByte tag;

        //常量池静态计数（因为从Class文件读取是顺序进行的）
        public static int global_index = 0;

        //在Constant_pool中的位置
        public int index;

        public ConstantInfo(uByte uByte) {
            tag = uByte;
            this.index = global_index++;
        }

        public void show() {
            System.out.print("#" + index + "\t");
        }

        //CONSTANT_Utf8_info                        tag 1
        static class ConstantUtf8Info extends ConstantInfo {
            //该utf8字符串长度                        u2
            public uByte length;
            /*  utf8缩略编码:
                '\u0001'    ----    '\u007f' (1 - 127 ASCII码)       U1
                '\u0080'    ----    '\u07ff' (128 - 2047)           U2
                '\u0800'    ----    '\uffff' (2048 - 65535)         U3
             */
            public uByte[] bytes;

            public ConstantUtf8Info(uByte uByte) {
                super(uByte);
                //读取字符串长度
                length = new uByte(U2);
                //读取字符串数据
                bytes = new uByte[BytesUtil.bytes2Int(length.getValue())];
                for (int i = 0; i < bytes.length; i++) {
                    //'\u0001'    ----    '\u007f' (1 - 127 ASCII码)       U1
                    bytes[i] = new uByte(U1);
                    //如果超过 0080 则加一个字节
                    // '\u0080'    ----    '\u07ff' (128 - 2047)           U2
                    if (BytesUtil.bytes2Int(bytes[i].getValue()) > 127) {
                        uByte sec = new uByte(U1);
                        bytes[i].append(sec.getValue());
                    }
                    //如果超过0800 则再加一个字节
                    //'\u0800'    ----    '\uffff' (2048 - 65535)         U3
                    if (BytesUtil.bytes2Int(bytes[i].getValue()) > 2047) {
                        uByte third = new uByte(U1);
                        bytes[i].append(third.getValue());
                    }
                }
            }

            @Override
            public void show() {
                super.show();
                System.out.print("tag:\tUft8\t");
                System.out.println("length:\t" + length + "\t" + "data:\t" + bytesString());
            }

            public String bytesString() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(BytesUtil.bytes2HexString(bytes[i].getValue()));
                }
                return sb.toString();
            }
        }

        //CONSTANT_Integer_info                     tag 3
        static class ConstantIntegerInfo extends ConstantInfo {
            //按照高位在前存储int 值
            public uByte int_value;

            public ConstantIntegerInfo(uByte uByte) {
                super(uByte);
                //读取int 值
                int_value = new uByte(U4);
            }

            @Override
            public void show() {
                super.show();
                System.out.print("tag:\tInteger\t");
                System.out.println("value:" + int_value);
            }
        }

        //CONSTANT_Float_info                       tag 4
        static class ConstantFloatInfo extends ConstantInfo {
            //按照高位在前存储float
            public uByte float_value;

            public ConstantFloatInfo(uByte uByte) {
                super(uByte);
                //读取float
                float_value = new uByte(U4);
            }

            @Override
            public void show() {
                super.show();
                System.out.print("tag:\tFloat\t");
                System.out.println("value:" + float_value);
            }
        }

        //CONSTANT_Long_info                        tag 5
        static class ConstantLongInfo extends ConstantInfo {
            //按照高位在前存储long
            public uByte long_value;

            public ConstantLongInfo(uByte uByte) {
                super(uByte);
                long_value = new uByte(U8);
            }

            @Override
            public void show() {
                super.show();
                System.out.print("tag:\tLong\t");
                System.out.println("value:" + long_value);
            }
        }

        //CONSTANT_Double_info                      tag 6
        static class ConstantDoubleInfo extends ConstantInfo {
            //按照高位在前存储double
            public uByte double_value = new uByte(U8);


            public ConstantDoubleInfo(uByte uByte) {
                super(uByte);
                double_value = new uByte(U8);
            }

            @Override
            public void show() {
                super.show();
                System.out.print("tag:\tDouble\t");
                System.out.println("value:" + double_value);
            }
        }

        //CONSTANT_Class_info                       tag 7
        static class ConstantClassInfo extends ConstantInfo {
            //索引值，指向全限定名常量项索引
            public uByte name_index;

            public ConstantClassInfo(uByte uByte) {
                super(uByte);
                name_index = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.print("tag:\tClass\t");
                System.out.println("name_index:#" + BytesUtil.bytes2Int(name_index.getValue()));
            }
        }

        //CONSTANT_String_info                       tag 8
        static class ConstantStringInfo extends ConstantInfo {
            //索引值，指向字符串字面量的索引
            public uByte string_index = new uByte(U2);

            public ConstantStringInfo(uByte uByte) {
                super(uByte);
                string_index = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.print("tag:\tString\t");
                System.out.println("string_index:#" + BytesUtil.bytes2Int(string_index.getValue()));
            }
        }

        //CONSTANT_Fieldref_info                      tag 9
        static class ConstantFieldRefInfo extends ConstantInfo {
            //索引值，指向声明字段的类或者接口描述符CONSTANT_Class_info的索引项
            public uByte class_index;
            //索引值，指向字段描述符CONSTANT_NameAndType的索引项
            public uByte name_index;

            public ConstantFieldRefInfo(uByte uByte) {
                super(uByte);
                class_index = new uByte(U2);
                name_index = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.println("tag:\tFieldRef\t");
                System.out.println("class_index:#"
                        + BytesUtil.bytes2Int(class_index.getValue())
                        + "\t" + "name_index:#"
                        + BytesUtil.bytes2Int(name_index.getValue()));
            }
        }

        //CONSTANT_Methodref_info                      tag 10
        static class ConstantMethodRefInfo extends ConstantInfo {
            //索引值，指向声明方法的类或者接口描述符CONSTANT_Class_info的索引项
            public uByte class_index;
            //索引值，指向字段描述符CONSTANT_NameAndType的索引项
            public uByte name_index;

            public ConstantMethodRefInfo(uByte uByte) {
                super(uByte);
                class_index = new uByte(U2);
                name_index = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.println("tag:\tMethodRef\t");
                System.out.println("class_index:#"
                        + BytesUtil.bytes2Int(class_index.getValue())
                        + "\t" + "name_index:#"
                        + BytesUtil.bytes2Int(name_index.getValue()));
            }
        }

        //CONSTANT_Interfaceref_info                    tag 11
        static class ConstantInterfaceRefInfo extends ConstantInfo {
            //索引值，指向声明接口的类或者接口描述符CONSTANT_Class_info的索引项
            public uByte class_index;
            //索引值，指向字段描述符CONSTANT_NameAndType的索引项
            public uByte name_index;

            public ConstantInterfaceRefInfo(uByte uByte) {
                super(uByte);
                class_index = new uByte(U2);
                name_index = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.println("tag:\tInterfaceRef\t");
                System.out.println("class_index:#"
                        + BytesUtil.bytes2Int(class_index.getValue())
                        + "\t" + "name_index:#"
                        + BytesUtil.bytes2Int(name_index.getValue()));
            }
        }

        //CONSTANT_NameAndType_info                    tag 12
        static class ConstantNameAndTypeInfo extends ConstantInfo {
            //索引值，指向该字段或方法名称常量项的索引          (方法名称常量项)
            public uByte constant_name_index;
            //索引值，指向该字段或该方法描述符常量项的索引       (方法描述符常量项)
            public uByte constant_describe_index;

            public ConstantNameAndTypeInfo(uByte uByte) {
                super(uByte);
                constant_name_index = new uByte(U2);
                constant_describe_index = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.println("tag:\tNameAndType\t");
                System.out.println("constant_name_index:#"
                        + BytesUtil.bytes2Int(constant_name_index.getValue())
                        + "\t" + "constant_describe_index:#"
                        + BytesUtil.bytes2Int(constant_describe_index.getValue()));
            }
        }

        //CONSTANT_MethodHandle_info                    tag 15
        static class ConstantMethodHandleInfo extends ConstantInfo {
            //值必须在1-9之间(包括1-9) 它决定了方法句柄的类型   方法句柄类型的值表示方法句柄的字节码行为
            public uByte reference_kind;
            //值必须是对常量池的有效索引
            public uByte reference_index;

            public ConstantMethodHandleInfo(uByte uByte) {
                super(uByte);
                reference_kind = new uByte(U1);
                reference_index = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.println("tag:\tMethodHandle\t");
                System.out.println("reference_kind:"
                        + BytesUtil.bytes2Int(reference_kind.getValue())
                        + "\t" + "reference_index:#"
                        + BytesUtil.bytes2Int(reference_index.getValue()));
            }
        }

        //CONSTANT_MethodType_info                      tag 16
        static class ConstantMethodTypeInfo extends ConstantInfo {
            //值必须是对常量池的有效索引，常量池在该索引出的项必须是CONSTANT_Utf8_info结构，表示方法的描述符
            public uByte desriptor_index;

            public ConstantMethodTypeInfo(uByte uByte) {
                super(uByte);
                desriptor_index = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.println("tag:\tMethodType\t");
                System.out.println("descriptor_index:#" + BytesUtil.bytes2Int(desriptor_index.getValue()));
            }
        }

        //CONSTANT_InvokeDynamic_info                    tag 18
        static class ConstantInvokeDynamicInfo extends ConstantInfo {
            //值必须是对当前Class文件中引导方法表的bootstrap_methods[]数组的有效索引
            public uByte bootstrap_method_attr_index;

            //值必须是对当前常量池的有效索引，常量池在该索引处的项必须是CONSTANT_NameAndType_info结构，表示方法名和方法描述符
            public uByte name_and_type;

            public ConstantInvokeDynamicInfo(uByte uByte) {
                super(uByte);
                bootstrap_method_attr_index = new uByte(U2);
                name_and_type = new uByte(U2);
            }

            @Override
            public void show() {
                super.show();
                System.out.println("tag:\tInvokeDynamic\t");
                System.out.println("bootstrap_method_attr_index:#"
                        + BytesUtil.bytes2Int(bootstrap_method_attr_index.getValue())
                        + "\t" + "name_and_type:#"
                        + BytesUtil.bytes2Int(name_and_type.getValue()));
            }
        }

        static ConstantInfo constantInfoBuilder() {
            uByte tag = new uByte(U1);
            switch (BytesUtil.bytes2Int(tag.getValue())) {
                case CONSTANT_Utf8_info_TAG:
                    return new ConstantUtf8Info(tag);
                case CONSTANT_Interger_info_TAG:
                    return new ConstantIntegerInfo(tag);
                case CONSTANT_Float_info_TAG:
                    return new ConstantFloatInfo(tag);
                case CONSTANT_Long_info_TAG:
                    return new ConstantLongInfo(tag);
                case CONSTANT_Double_info_TAG:
                    return new ConstantDoubleInfo(tag);
                case CONSTANT_Class_info_TAG:
                    return new ConstantClassInfo(tag);
                case CONSTANT_String_info_TAG:
                    return new ConstantStringInfo(tag);
                case CONSTANT_Fieldref_info_TAG:
                    return new ConstantFieldRefInfo(tag);
                case CONSTANT_Methodref_info_TAG:
                    return new ConstantMethodRefInfo(tag);
                case CONSTANT_InterfaceMethodref_info_TAG:
                    return new ConstantInterfaceRefInfo(tag);
                case CONSTANT_NameAndType_info_TAG:
                    return new ConstantNameAndTypeInfo(tag);
                case CONSTANT_MethodHandle_info_TAG:
                    return new ConstantMethodHandleInfo(tag);
                case CONSTANT_MethodType_info_TAG:
                    return new ConstantMethodTypeInfo(tag);
                case CONSTANT_InvokeDynamic_info_TAG:
                    return new ConstantInvokeDynamicInfo(tag);
                default:
                    break;
            }
            return null;
        }

    }

    //常量数组
    private ConstantInfo[] constant_pool;

    //----------------------------------Constant Pool  End--------------

    /*
        name:   访问标志
        length: U2
        feature:这个类的访问标志
    */
    private uByte access_flags;


    /*
        0       0       0       0           0       0       0       0
              ENUM  ANNOTATION SYNTHETIC          ABSTRACT  INTERFACE

        0       0       0       0           0       0       0       0
                      SUPER   FINAL                               PUBLIC
     */
    static class AccessFlagHelper {
        //是否为public 类型
        static final int ACC_PUBLIC = 0x0001;
        //是否被声明为final  只有类可以设置
        static final int ACC_FINAL = 0x0010;
        //是否允许使用invokespecial 字节码指令的新语义,  jdk 1.02 后编译出来的这个值都为真
        static final int ACC_SUPER = 0x0020;
        //标识这是一个接口
        static final int ACC_INTERFACE = 0x0200;
        //是否为abstract类型，对于接口或者抽象类来说，此标志值为真，其他类为假
        static final int ACC_ABSTRACT = 0x0400;
        //表示这个类并非由用户代码生成
        static final int ACC_SYNTHETIC = 0x1000;
        //标识这是一个注解
        static final int ACC_ANNOTATION = 0x2000;
        //标识这是一个枚举
        static final int ACC_ENUM = 0x4000;


        static enum AccFlags {
            //是否为public 类型
            ACC_PUBLIC,
            //是否被声明为final  只有类可以设置
            ACC_FINAL,
            //是否允许使用invokespecial 字节码指令的新语义,  jdk 1.02 后编译出来的这个值都为真
            ACC_SUPER,
            //标识这是一个接口
            ACC_INTERFACE,
            //是否为abstract类型，对于接口或者抽象类来说，此标志值为真，其他类为假
            ACC_ABSTRACT,
            //表示这个类并非由用户代码生成
            ACC_SYNTHETIC,
            //标识这是一个注解
            ACC_ANNOTATION,
            //标识这是一个枚举
            ACC_ENUM
        }

        //取得标志的16位索引
        static int getIndex(int flag) {
            return (int) (Math.log(flag) / Math.log(2));
        }

        static boolean[] accFlag(uByte access_flags) {
            //获取标志
            int flag = BytesUtil.bytes2Int(access_flags.getValue());
            //两个字节  16位
            boolean[] flags = new boolean[16];
            //判断是否public
            if ((flag & ACC_PUBLIC) == ACC_PUBLIC) {
                flags[getIndex(ACC_PUBLIC)] = true;
            }

            //判断final
            if ((flag & ACC_FINAL) == ACC_FINAL) {
                flags[getIndex(ACC_FINAL)] = true;
            }

            //super 默认真
            flags[getIndex(ACC_SUPER)] = true;

            //是否为接口
            if ((flag & ACC_INTERFACE) == ACC_INTERFACE) {
                flags[getIndex(ACC_INTERFACE)] = true;
            }

            //是否为抽象类
            if ((flag & ACC_ABSTRACT) == ACC_ABSTRACT) {
                flags[getIndex(ACC_ABSTRACT)] = true;
            }

            //表示这个类并非由用户代码生成
            if ((flag & ACC_SYNTHETIC) == ACC_SYNTHETIC) {
                flags[getIndex(ACC_SYNTHETIC)] = true;
            }

            //表示是否注解
            if ((flag & ACC_ANNOTATION) == ACC_ANNOTATION) {
                flags[getIndex(ACC_ANNOTATION)] = true;
            }

            //表示是否枚举
            if ((flag & ACC_ENUM) == ACC_ENUM) {
                flags[getIndex(ACC_ENUM)] = true;
            }
            return flags;
        }
    }


    /*
        name:   类索引
        length: U2
        feature:确定这个类的全限定名称     指向常量池CONSTANT_Class_info索引 再指向CONSTANT_Utf8_info 可获取全名
    */
    private uByte this_class;

    /*
        name:   父类索引
        length: U2
        feature:指向这个类的父类，由于所有类都继承java.lang.Object  因此除了Object类super_class，其他都不为0
    */
    private uByte super_class;

    //----------------------------------Interfaces  Start--------------
    /*
        name:   接口计数
        length: U2
        feature:对接口的计数      如果没有实现接口 则为0
    */
    private uByte interfaces_count;

    /*
        接口描述
     */
    static class InterfaceInfo {
        private uByte interfaceInfo;

        public InterfaceInfo() {
            interfaceInfo = new uByte(U2);
        }
    }

    /*
       name:   接口数组
       length: interfaces_count
       feature:接口的实际数组
   */
    private InterfaceInfo[] interfaces;


    //----------------------------------Interfaces  End--------------

    //----------------------------------Fields  Start--------------
    /*
        name:   字段计数
        length: U2
        feature:对字段(Field进行计数)
    */
    private uByte fields_count;

    static class FieldInfo {
        /*
            name:   访问标志
            length: U2
            feature:确定变量的访问标志
        */
        uByte access_flags;

        /*
            name:  名称索引
            length: U2
            feature:指向常量池的引用   代表字段的简单名称（比如int a;  简单名称为:a）
        */
        uByte name_index;

        /*
            name:   描述符索引
            length: U2
            feature:指向常量池中的引用  代表方法的描述符

            描述符：基本数据类型(byte,char,double,float,int,long,short,boolean)以及代表无返回值的void类型都要有一个大写字符表示，对象用L加对象全限定名称
            标识符：                        含义:
            B                             byte
            C                             char
            D                             double
            F                             float
            I                             int
            J                             long
            S                             short
            Z                             boolean
            V                             void
            L                             对象类型，如java.lang.Object  ->>> Ljava/lang/Object;
            [                             代表一个维度数组  如：java.lang.String[][][] ->>>  [[[Ljava/lang/String;
        */
        uByte descriptor_index;

        /*
            name:   属性表计数
            length: U2
            feature:描述字段额外的信息  如 final static int m=123;  就会有一项 ConstantValue的属性
        */
        uByte attributes_count;

        /*
            name:   属性表
            length: attributes_count
            feature:属性值
        */
        AttributeInfo[] attributes;

        public FieldInfo() {
            access_flags = new uByte(U2);
            name_index = new uByte(U2);
            descriptor_index = new uByte(U2);
            attributes_count = new uByte(U2);
            attributes = new AttributeInfo[BytesUtil.bytes2Int(attributes_count.getValue())];
            for (int i = 0; i < BytesUtil.bytes2Int(attributes_count.getValue()); i++) {
                attributes[i] = new AttributeInfo();
            }
        }
    }

    /*
        name:   字段表
        length: fields_count
        feature:描述字段
    */
    private FieldInfo[] fields;

    //----------------------------------Fields  End--------------


    //----------------------------------Methods  Start--------------
    /*
        name:   方法计数
        length: U2
        feature:方法数量
    */
    private uByte methods_count;

    static class MethodInfo {
        /*
           name:   访问标志
           length: U2
           feature:确定变量的访问标志
       */
        uByte access_flags;

        /*
            name:  名称索引
            length: U2
            feature:指向常量池的引用   代表字段的简单名称（比如int a;  简单名称为:a）
        */
        uByte name_index;

        /*
            name:   描述符索引
            length: U2
            feature:指向常量池中的引用  代表方法的描述符

            描述符：基本数据类型(byte,char,double,float,int,long,short,boolean)以及代表无返回值的void类型都要有一个大写字符表示，对象用L加对象全限定名称
            标识符：                        含义:
            B                             byte
            C                             char
            D                             double
            F                             float
            I                             int
            J                             long
            S                             short
            Z                             boolean
            V                             void
            L                             对象类型，如java.lang.Object  ->>> Ljava/lang/Object;
            [                             代表一个维度数组  如：java.lang.String[][][] ->>>  [[[Ljava/lang/String;
        */
        uByte descriptor_index;

        /*
           name:   属性表计数
           length: U2
           feature:描述字段额外的信息  如 final static int m=123;  就会有一项 ConstantValue的属性
       */
        uByte attributes_count;

        /*
            name:   属性表
            length: attributes_count
            feature:属性值
        */
        AttributeInfo[] attributes;

        @SuppressWarnings("Duplicates")
        public MethodInfo() {
            access_flags = new uByte(U2);
            name_index = new uByte(U2);
            descriptor_index = new uByte(U2);
            attributes_count = new uByte(U2);
            attributes = new AttributeInfo[BytesUtil.bytes2Int(attributes_count.getValue())];
            for (int i = 0; i < BytesUtil.bytes2Int(attributes_count.getValue()); i++) {
                attributes[i] = new AttributeInfo();
            }
        }
    }
    /*
        name:   方法数组
        length: methods_count
        feature:方法表
    */
    private MethodInfo[] methods;
    //----------------------------------Methods  End--------------

    //----------------------------------Attributes  Start--------------
    //属性计数                          U2;
    private uByte attributes_count;

    static class AttributeInfo {

    }
    //属性数组

    //----------------------------------Attributes  End--------------

}
