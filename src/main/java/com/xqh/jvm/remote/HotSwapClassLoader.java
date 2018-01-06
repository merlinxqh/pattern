package com.xqh.jvm.remote;

/**
 * Created by leo on 2018/1/6.
 * 为了多次载入执行类 二加入的 加载器
 * 把defineClass非凡开放出来,只有外部显示调用的时候才会使用到loadByte方法
 * 由虚拟机调用时, 仍然按照原有的双亲委派规则使用loadClass方法进行类加载
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader(){
        super(HotSwapClassLoader.class.getClassLoader());
    }

    /**
     * HotSwapClassLoader所做的事情仅仅是公开父类 中的protected方法 defineClass()
     * 我们将会使用这个方法把提交执行的java类的byte[]数组 转变成Class对象. HotSwapClassLoader中并没有
     * 重写loadClass()或者findClass()方法,因此如果不算外部手工调用loadByte()方法的话, 这个类加载器的类
     * 查找范围与它父类加载器是完全一致的,在被虚拟机调用时, 它会按照双亲委派模型交给父类加载.
     * 构造函数中指定为加载HotSwapClassLoader类的类加载器作为父类加载器, 这一步实现提交的执行代码可以访问服务器引用类库的关键,
     *
     * @param classByte
     * @return
     */
    public Class loadByte(byte[] classByte){
        return defineClass(null, classByte, 0, classByte.length);
    }
}
