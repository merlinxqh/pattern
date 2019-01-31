package com.xqh.study.pattern.command;

/**
 * @Description: 遥控器
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年12月7日 上午10:59:59
 * @version V1.0 
 *
 */
public class RemoteControl {
	
  Command[] onCommands;
  Command[] offCommands;
  Command undoCommand;
  
  
  //构造器中,实例化并初始化这两个开关的按钮
  public RemoteControl(){
	  onCommands=new Command[7];
	  offCommands=new Command[7];
	  
	  for(int i=0;i<7;i++){
		  onCommands[i]=new NoCommand();
		  offCommands[i]=new NoCommand();
	  }
	  undoCommand=new NoCommand();
  }
  
  /**
   * 
   * @param solt   开关插槽位置
   * @param onCommand  开的命令
   * @param offCommand  关的命令
   */
  public void setCommand(int solt,Command onCommand,Command offCommand){
	  onCommands[solt]=onCommand;
	  offCommands[solt]=offCommand;
  }
  
  /**
   * 按开
   * @param solt
   */
  public void onButtonPress(int solt){
	  onCommands[solt].excute();
	  undoCommand=offCommands[solt];
  }
  
  /**
   * 按 关
   * @param solt
   */
  public void offButtonPress(int solt){
	  offCommands[solt].excute();
	  undoCommand=onCommands[solt];
  }
  
  
  /**
   * 撤销操作
   */
  public void undoButtonPress(){
	  this.undoCommand.excute();
  }
  
  public String toString(){
	  StringBuffer sb=new StringBuffer();
	  sb.append("\n----------  Remote Control ------------ \n");
	  for(int i=0;i<onCommands.length;i++){
		  sb.append("[solt "+i+"]"+onCommands[i].getClass().getName()+"     "+offCommands[i].getClass().getName()+"\n");
	  }
	  return sb.toString();
  }
}
