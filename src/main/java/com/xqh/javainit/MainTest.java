package com.xqh.javainit;

public class MainTest {
	
	/**
	 * 
	 * 何时开始类的初始化 什么情况下需要开始 类加载过程的第一阶段:”加载”. 虚拟机规范中并没强行约束, 这点可以交给虚拟机的具体实现自由把握,
	 * 但是对于初始化阶段虚拟机规范是 严格规定了 如以下几种情况, 如果类未初始化会对类进行初始化. 
	 * 1. 创建类的实例
	 * 2. 访问类的静态变量 (除
	 * 常量[被final修饰的静态变量] 原因: 常量是一种特殊的变量, 因为编译器把他们当做 值 (value) 而不是 域(field)来对待.
	 * 如果你的代码中用到了 常变量(constant variable), 编译器并不会生成字节码来从对象中载入域的值,
	 * 而是直接把这个值插入到字节码中. 这是一种很有用的优化, 但是如果你需要改变final域的值 那么每一块用到那个域的代码都需要重新编译.) 
	 * 3.访问类的静态方法. 
	 * 4. 反射(Class.forName(“com.bainuo.test”) 5. 当初始化一个类时,发现其父类还未初始化,
	 * 则先 触发父类的初始化. 6. 虚拟机启动时, 定义了main()方法的那个类先初始化.
	 * 
	 * 以上情况称为对一个类进行”主动引用”,除此情况之外,均不会触发类的初始化,称为 ”被动引用”.
	 * 
	 */

	/**
	 * 被动引用的例子 1. 子类调用父类的静态变量,子类不会被初始化,只有父类被初始化. 对于静态字段,只有定义这个字段的类才会被初始化. 2.
	 * 通过数组定义的引用类,不会触发类的初始化. 3. 访问类的常量,不会初始化类.
	 */
	
   public static void main(String[] args) {
	   
	   /**
	    * 调用父类静态变量 
	    */
//	   System.out.println(SubClass.value);
//	   
//	   /**
//	    * 数组定义引用类
//	    */
//	   SubClass[] ss=new SubClass[10];
	   
	   /**
	    * 访问类的常量
	    */
//	   System.out.println(SubClass.CONSTANT_VARIABLE);
	   
	   /**
	    * 访问类的静态变量 初始化 类
	    */
	   System.out.println(SubClass.STATIC_VARIABLE);
	   
	   
   }
}
