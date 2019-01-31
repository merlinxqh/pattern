package com.xqh.study.pattern.command;

/**
 * @Description: 关灯命令
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年12月7日 上午11:27:21
 * @version V1.0 
 *
 */
public class LightOffCommand implements Command{
	Light light;
	public LightOffCommand(Light light){
		this.light=light;
	}

	public void excute() {
		light.off();
	}

	public void undo() {
        light.on();		
	}

}
