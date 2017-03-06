package com.xqh.pattern.decorate;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @Description: 编写i/o装饰者 
 * 将大小字母变小写
 * @author qh.xu
 * @email  qianghui.xu@ffzxnet.com
 * @date 2016年10月24日 下午4:53:43
 * @version V1.0 
 *
 */
public class LowerCaseInputStream extends FilterInputStream { 
	protected LowerCaseInputStream(InputStream in) {
		super(in);
	}

	@Override
	public int read() throws IOException {
		int c=super.read();
		return (c == -1 ? c: Character.toLowerCase((char)c));
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int result=super.read(b, off, len);
		for(int i=off;i<off+result;i++){
			b[i]=(byte)Character.toLowerCase((char)b[i]);
		}
		return result;
	}
	
	

}
