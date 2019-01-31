/**
 * Created by leo on 2017/10/17.
 * 使用Akka构建高并发程序
 * 新并发模型Actor
 *   在Actor模型中,我们失去了对对象的方法调用, 我们并不是通过Actor对象的某一个方法告诉Actor需要做什么,
 *   而是给Actor发送一条消息. 当Actor收到消息, 它有可能根据消息的内容做出某些行为, 包括更改自身状态.
 *   但是, 在这种情况下, 这种状态的更改都是Actor自己进行的, 并不是被外界被迫进行的.
 */
package com.xqh.study.juc.akka;