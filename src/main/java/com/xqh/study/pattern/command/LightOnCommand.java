package com.xqh.study.pattern.command;

 /**
 * @Description: 开灯 命令
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年12月6日 上午10:07:45
 * @version V1.0 
 *
 */
public class LightOnCommand implements Command {
	
	Light light;
	
	public LightOnCommand(Light light){
		this.light=light;
	}

	public void excute() {
		light.on();
	}

	public void undo() {
		light.off();
	}

}
