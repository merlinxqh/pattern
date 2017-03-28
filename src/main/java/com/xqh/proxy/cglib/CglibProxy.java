package com.xqh.proxy.cglib;

import java.lang.reflect.Method;

import com.xqh.proxy.TargetInterface;
import com.xqh.proxy.TargetInterfaceImpl;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @author      leo.xu
 * @description Cglib动态代理
 * @date        2017年3月28日 上午9:53:29
 * @email       leo.xu@bainuo.com
 */
public class CglibProxy implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.out.println("before...."+methodProxy.getSuperName());
		System.out.println(method.getName());
		Object o=methodProxy.invokeSuper(obj, args);
		System.out.println("after...."+methodProxy.getSuperName());
		return o;
	}
	
	
	public static void main(String[] args) {
		MethodInterceptor handler=new CglibProxy();
		Enhancer hancer=new Enhancer();
		hancer.setSuperclass(TargetInterfaceImpl.class);
		hancer.setCallback(handler);
		TargetInterface targetProxy=(TargetInterface) hancer.create();
		targetProxy.method("aaaaa");
	}

}
