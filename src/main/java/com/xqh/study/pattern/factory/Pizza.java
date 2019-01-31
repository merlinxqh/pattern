package com.xqh.study.pattern.factory;

 /**
 * @Description: 披萨类
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月26日 下午3:04:05
 * @version V1.0 
 *
 */
public abstract class Pizza {
	
    public void prepaer(){
    	System.out.println("准备披萨...");
    }
    
    public void bake(){
    	System.out.println("烤披萨...");
    }
    
    public void cut(){
        System.out.println("切披萨...");
    }
    
    public void box(){
    	System.out.println("包装披萨...");
    }
}
