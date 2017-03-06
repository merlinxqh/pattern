package com.xqh.pattern.strategy.impl;

import com.xqh.pattern.strategy.QuackBehavior;

public class GuaGuaQuack implements QuackBehavior {

	public void quack() {
		System.out.println("呱呱叫...");
	}

}
