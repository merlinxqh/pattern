package com.xqh.juc.design.producerConsumer.cpuCache;

/**
 * Created by leo on 2017/10/10.
 */
public class FalseSharing implements Runnable {

    public final static int NUM_THREADS=2;//根据电脑cpu核数
    public final static long ITERATIONS=500L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static{
        for(int i=0;i< longs.length;i++){
            longs[i]=new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex){
        this.arrayIndex=arrayIndex;
    }

    @Override
    public void run() {
        long i=ITERATIONS+1;
        while (0 != --i){
            longs[arrayIndex].value=i;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final long start=System.currentTimeMillis();
        runTest();
        System.out.println("duration = "+(System.currentTimeMillis() - start));
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads=new Thread[NUM_THREADS];

        for(int i=0;i<threads.length;i++){
            threads[i]=new Thread(new FalseSharing(i));
        }

        for(Thread t:threads){
            t.start();
        }
        for(Thread t:threads){
            t.join();
        }
    }


    public final static class VolatileLong{
       public volatile long value=0L;
       public long p1,p2,p3,p4,p5,p6,p7;// comment out
    }
}
