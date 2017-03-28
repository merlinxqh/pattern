package com.xqh.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * @author      leo.xu
 * @description TODO
 * @date        2017年3月28日 上午8:55:17
 * @email       leo.xu@bainuo.com
 */
public class ProxyInvocationHandler implements InvocationHandler{
    
	
	private Object target;
	
	public ProxyInvocationHandler(Object target){
		this.target=target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("前置通知....");
		method.invoke(target, args);
		System.out.println("后置通知....");
		return null;
	}
	
	public static void main(String[] args) {
		TargetInterface target=new TargetInterfaceImpl();
		ProxyInvocationHandler handler=new ProxyInvocationHandler(target);
		TargetInterface proxyObject=(TargetInterface) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
		proxyObject.method("hehe");
	}

}
