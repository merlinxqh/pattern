package com.xqh.pattern.strategy.impl;

import com.xqh.pattern.strategy.QuackBehavior;

public class JiaJiaQuack implements QuackBehavior {

	public void quack() {
		System.out.println("戛戛加...");
	}

}
