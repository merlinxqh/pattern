package com.xqh.study.pattern.command;

/**
 * 
 * @Description: 命令 通用接口
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年12月6日 上午10:07:06
 * @version V1.0 
 *
 */
public interface Command {
   public void excute();
   
   /**
    * 撤销操作
    */
   public void undo();
}
