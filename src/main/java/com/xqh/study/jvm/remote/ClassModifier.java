package com.xqh.study.jvm.remote;

/**
 * Created by leo on 2018/1/6.
 * 修改class文件, 暂时只提供修改常量池常量的功能.
 *
 *
 *
 * 经过ClassModify处理后的byte[]数组才会传给HotSwapClassLoader.loadByte()方法进行类加载
 * byte[]数组在这里替换符号引用之后, 与客户端直接在Java代码中应用HackSystem类再编译生成的class是完全一样的,
 * 这样的实现既避免了客户端编写临时执行代码时要依赖特定的类 (不然无法引入HackSystem), 又表面了服务端修改标准输出后影响到其他程序的输出.
 *
 */
public class ClassModifier {

    /**
     * Class文件中常量池的其实偏移量
     * 前四个 摩尔数 , 后四个 版本号
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    /**
     * CONSTANT_Utf8_inf常量的tag标志
     */
    private static final int CONSTANT_Utf8_info = 1;

    /**
     * 常量池中11中常量所占的长度 , CONSTANT_Utf8_info型常量除外, 因为它不是定长的.
     */
    private static final  int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};

    private static final int u1 = 1;

    private static final int u2 = 2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte){
        this.classByte = classByte;
    }

    /**
     * 修改常量池中CONSTANT_Utf8_info常量的内容
     * @param oldStr 修改前的字符串
     * @param newStr 修改后的字符串
     * @return 修改结果
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr){
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;

        for(int i = 0; i < cpc; i++){
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);
            if(tag == CONSTANT_Utf8_info){
                int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);
                offset += (u1 + u2);
                String str = ByteUtils.bytes2String(classByte, offset, len);
                if(str.equalsIgnoreCase(oldStr)){
                    byte[] strBytes = ByteUtils.strign2Bytes(newStr);
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
                    classByte = ByteUtils.bytesReplace(classByte, offset - u2,u2, strLen);
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
                    return classByte;
                }else{
                    offset += len;
                }
            }else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classByte;
    }

    public int getConstantPoolCount(){
        return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}
