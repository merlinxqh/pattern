package com.xqh.study.pattern.command;

 /**
 * @Description: 使用命令对象
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年12月6日 上午10:13:09
 * @version V1.0 
 *
 */
public class SampleRemoteControl {
   
	Command solt;
	
	public void setCommand(Command solt){
		this.solt=solt;
	}
	
	
	public void buttonWasPressed(){
		this.solt.excute();
	}
}
