package com.xqh.study.jvm.remote;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by leo on 2018/1/6.
 * JavaClass执行工具
 */
public class JavaClassExecuter {

    /**
     * 执行外部传过来的代表一个Java类的byte数组
     * 将输入类的byte数组中代表java.lang.System的CONSTANT_Utf8_info常量修改为劫持后的HackSystem类
     *
     * 执行方法为该类的static main(String[] args)方法, 输出结果为该类向System.out/err输出的信息
     * @param classByte 代表一个java类的数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte){
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);

        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System","com.xqh.study.jvm.remote/HackSystem");

        HotSwapClassLoader loader = new HotSwapClassLoader();

        Class clazz = loader.loadByte(modiBytes);

        try {
            Method method = clazz.getMethod("main",new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return HackSystem.getBufferString();
    }
}
