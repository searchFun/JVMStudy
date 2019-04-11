package 第7章类加载.classLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * @program: JVMStudy
 * @description: Class读取
 * @author: hjc
 * @create: 2019-01-16 10:26
 */
public class ClassReaderrr {

    public static int U1 = 1;
    public static int U2 = 2;
    public static int U3 = 3;
    public static int U4 = 4;
    public static int U8 = 8;

    //读取文件流类型
    static class uByte {
        private String value;
        public int length;
        private static InputStream inputStream;

        public uByte(int length) {
            this.length = length;
            switch (length) {
                case 1:
                    read(in -> readFromInputStream(U1));
                    break;
                case 2:
                    read(in -> readFromInputStream(U2));
                    break;
                case 3:
                    read(in -> readFromInputStream(U3));
                    break;
                case 4:
                    read(in -> readFromInputStream(U4));
                    break;
                case 8:
                    read(in -> readFromInputStream(U8));
                    break;
                default:
                    break;
            }
        }

        public void append(uByte uByte) {
            this.value = this.value + " " + uByte;
        }

        public static void setInputStream(InputStream inputStream) {
            uByte.inputStream = inputStream;
        }

        public void read(Function<InputStream, String> function) {
            try {
                this.value = function.apply(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String readFromInputStream(int length) {
            byte[] bytes = new byte[length];
            try {
                inputStream.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bytesToString(bytes);
        }

        @Override
        public String toString() {
            return this.value;
        }
    }


    //access_flags                  U2              1
    private uByte access_flags;
    //this_class                    U2              1
    private uByte this_class;
    //super_class                   U2              1
    private uByte super_class;
    //----------------------------------Interface  Start--------------
    //interfaces_count              U2              1
    private uByte interfaces_count;

    private class InterfaceReader {

    }

    //interfaces                    U2         interfaces_count
    private InterfaceReader[] interfaces;

    private static void interfacesReader(ClassReaderrr classReaderrr) throws Exception {
        //interfaces_count              U2;
        classReaderrr.interfaces_count = new uByte(U2);
        //interfaces                    U2;
    }
    //----------------------------------Interface  Start--------------

    //----------------------------------Field  Start--------------
    //fields_count                  U2              1
    private uByte fields_count;

    //fields                ConstantInfo   fields_count
    private class FieldReader {

    }

    private FieldReader[] fields;

    private static void fieldsReader(ClassReaderrr classReaderrr) throws Exception {
        //fields_count                  U2;
        classReaderrr.fields_count = new uByte(U2);

        //fields                                    fields_count
    }
    //----------------------------------Field  End--------------

    //----------------------------------Method  Start--------------
    //methods_count                 U2              1
    private uByte methods_count;

    //methods                   MethodReader    methods_count
    private class MethodReader {

    }

    private MethodReader[] methods;

    private static void methodsReader(ClassReaderrr classReaderrr) throws Exception {
        //methods_count                 U2;
        classReaderrr.methods_count = new uByte(U2);
        //methods                                   methods_count
    }
    //----------------------------------Method  End--------------

    //----------------------------------Attribute  Start--------------
    //attributes_count              U2               1
    private uByte attributes_count;

    //attributes             AttributeReader    attributes_count
    private class AttributeReader {

    }

    private AttributeReader[] attributes;

    private static void attributesReader(ClassReaderrr classReaderrr) throws Exception {
        //attributes_count               U2;
        classReaderrr.attributes_count = new uByte(U2);
        //attributes                                attributes_count
    }

    //----------------------------------Attribute  Start--------------
    //读取
    public ClassReaderrr(InputStream in) throws Exception {
        //设置输入流
        uByte.setInputStream(in);
        //magic                          U4;
        this.magic = new uByte(U4);
        //minor_version                 U2;
        this.minor_version = new uByte(U2);
        //major_version                 U2;
        this.major_version = new uByte(U2);
        //constant_pool
        constantPoolReader(this);
        //access_flags                  U2;
        this.access_flags = new uByte(U2);
        //this_class                    U2;
        this.this_class = new uByte(U2);
        //super_class                   U2;
        this.super_class = new uByte(U2);
        //access_flags                  U2;
        this.access_flags = new uByte(U2);
        //this_class                    U2;
        this.this_class = new uByte(U2);
        //super_class                   U2;
        this.super_class = new uByte(U2);
        //interfaces
        interfacesReader(this);
        //fields
        fieldsReader(this);
        //methods
        methodsReader(this);
        //attributes
        attributesReader(this);
    }

    //输出
    private void show() {
        System.out.println("--------------------------------------------------------");
        System.out.println("文件魔数(magic):" + this.magic);
        System.out.println("次版本号(minor_version):" + this.minor_version);
        System.out.println("主版本号(major_version):" + this.major_version);
        System.out.println("---------------------常量池开始---------------------------");
        for (int i = 1; i < constant_pool.length; i++) {
            this.constant_pool[i].sout();
        }
        System.out.println("---------------------常量池结束---------------------------");
        System.out.println("访问标志(access_flags):" + this.access_flags);
        System.out.println("This Class(this_class):" + this.this_class);
        System.out.println("Super Class(super_class):" + this.super_class);
        System.out.println("---------------------接口开始-----------------------------");
//        for (int i = 1; i < interfaces.length; i++) {
//            this.interfaces[i].sout();
//        }
        System.out.println("---------------------接口结束-----------------------------");
        System.out.println("---------------------变量开始-----------------------------");
//        for (int i = 1; i < fields.length; i++) {
//            this.fields[i].sout();
//        }
        System.out.println("---------------------变量结束-----------------------------");
        System.out.println("---------------------方法开始-----------------------------");
//        for (int i = 1; i < methods.length; i++) {
//            this.methods[i].sout();
//        }
        System.out.println("---------------------方法结束-----------------------------");
        System.out.println("---------------------参数开始-----------------------------");
//        for (int i = 1; i < attributes.length; i++) {
//            this.attributes[i].sout();
//        }
        System.out.println("---------------------参数结束-----------------------------");
        System.out.println("--------------------------------------------------------");
    }

    public static String hellClassPath = "/home/hjc/Documents/IdeaProjects/JVMStudy/out/production/JVMStudy/第7章类加载/classLoader/HelloClass.class";

    public static void main(String[] args) throws Exception {
        File file = new File(hellClassPath);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            ClassReaderrr classReaderrr = new ClassReaderrr(in);
            classReaderrr.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }


}

