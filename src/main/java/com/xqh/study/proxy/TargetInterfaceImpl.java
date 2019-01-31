package com.xqh.study.proxy;

public class TargetInterfaceImpl implements TargetInterface{

	@Override
	public void method(String param) {
		System.out.println("执行目标对象方法..."+param);
	}

}
