package com.xqh.study.jvm;

/**
 * Created by leo on 2017/11/20.
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK=null;

    public void isAlive(){
        System.out.println("yes, i am alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        SAVE_HOOK=this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        //对象第一次成功拯救自己
        SAVE_HOOK=null;
        System.gc();

        //因为finalize方法优先级很低,暂停0.5秒
        Thread.sleep(500);

        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no, i am dead :(");
        }


        /**
         *
         */
        SAVE_HOOK=null;
        System.gc();

        //因为finalize方法优先级很低,暂停0.5秒
        Thread.sleep(500);

        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no, i am dead :(");
        }
    }


}
