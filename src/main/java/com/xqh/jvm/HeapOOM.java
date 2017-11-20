package com.xqh.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/11/10.
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 堆内存溢出 测试
 */
public class HeapOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list=new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
