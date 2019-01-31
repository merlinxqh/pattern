package com.xqh.study.pattern.command;

/**
 * 
 * @Description: 遥控器 测试类
 * @author qh.xu
 * @email qianghui.xu@ffzxnet.com
 * @date 2016年12月6日 上午10:17:26
 * @version V1.0
 *
 */
public class RemoteControlTest {
	public static void main(String[] args) {
		
//         SampleRemoteControl con=new SampleRemoteControl();
//         
//         Light light=new Light();//请求的接受者
//         Command command=new LightOnCommand(light);//创建一个命令 将接受者传给它
//         con.setCommand(command);
//         con.buttonWasPressed();
//         
//         GarageDoor garage=new GarageDoor();
//         GarageDoorCommand gc=new GarageDoorCommand(garage);
//         con.setCommand(gc);
//         
//         con.buttonWasPressed();
		
		
		   RemoteControl remote=new RemoteControl();
		   Light light=new Light();
		   
		   LightOffCommand off=new LightOffCommand(light);
		   LightOnCommand on=new LightOnCommand(light);
		   
		   remote.setCommand(0, on, off);
		   
		   //开灯
		   remote.onButtonPress(0);
		   //撤销
		   remote.undoButtonPress();
		   
		   //关灯
		   remote.offButtonPress(0);
		   //撤销
		   remote.undoButtonPress();
         
	}
}
