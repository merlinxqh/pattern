/**
 * Created by leo on 2017/10/16.
 * 函数式 编程
 * 符合规范的函数式接口
 * 1. 函数式接口只能有一个抽象方法
 * 2. 任何被java.lang.Object实现的方法,都不能视为抽象方法.
 *    eg:
 *      boolean equals(Object obj);
 *
 *   java8之前,接口只能包含抽象方法. 但从java8之后,接口也可以包含若干个实例方法.
 */
package com.xqh.juc.functional;