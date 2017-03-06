package com.xqh.pattern.decorate;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputTest {
  public static void main(String[] args) {
	 int c;
	  try {
		InputStream in=new LowerCaseInputStream(new BufferedInputStream(new FileInputStream("D:/text.txt")));
			while((c=in.read())>=0){
				System.out.print((char)c);
			}
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
