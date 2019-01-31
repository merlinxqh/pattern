package com.xqh.study.juc.lock.stampedLock;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by leo on 2017/10/17.
 *
 * StampedLock通过使用乐观读来增加系统并行度.
 */
public class Point {
    private double x,y;

    private final StampedLock sl=new StampedLock();

    /**
     * 这是一个排它锁 (写入)
     * @param deltaX
     * @param deltaY
     */
    void move(double deltaX,double deltaY){
       long stamp=sl.writeLock();
       try {
           x += deltaX;
           y += deltaY;
       }finally {
           sl.unlockWrite(stamp);
       }
    }

    /**
     * 只读方法 读取x,y坐标
     * @return
     */
    double distanceFromOrigin(){

        /**
         * 试图尝试一次乐观读. 返回一个类似于时间戳的邮戳整数 stamp
         * 这个stamp作为这一次锁获取的凭证.
         */
        long stamp=sl.tryOptimisticRead();
       double currentX = x, currentY=y;
        /**
         * validate(stamp)方法判断这个stamp是否在读过程发生期间被修改过.
         * 如果没被修改过 直接返回结果
         *
         */
       if(!sl.validate(stamp)){
           /**
            * 将乐观锁 升级为 悲观锁
            */
           stamp = sl.readLock();
           try {
               currentX=x;
               currentY=y;
           }finally {
               sl.unlockRead(stamp);
           }
       }
       return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
