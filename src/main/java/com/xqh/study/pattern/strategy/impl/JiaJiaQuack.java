package com.xqh.study.pattern.strategy.impl;

import com.xqh.study.pattern.strategy.QuackBehavior;

public class JiaJiaQuack implements QuackBehavior {

	public void quack() {
		System.out.println("戛戛加...");
	}

}
