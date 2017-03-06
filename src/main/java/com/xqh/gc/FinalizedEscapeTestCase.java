package com.xqh.gc;

public class FinalizedEscapeTestCase {
	public static FinalizedEscapeTestCase caseForEscape = null;
	
	/**
	 * 执行gc之前调用finalize方法
	 */
    @Override
    protected void finalize() throws Throwable {
       super.finalize();
       System.out.println("哈哈，我已逃逸！");
       caseForEscape = this;
    }
}
