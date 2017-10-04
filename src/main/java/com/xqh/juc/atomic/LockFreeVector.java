package com.xqh.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 无锁的Vector实现
 *   特点: 可以根据需求动态扩展其内部空间
 */
public class LockFreeVector<E> {
    private static final int N_BUCKETS=30;
    private static final int FIRST_BUCKETS_SIZE=8;
    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
    private final AtomicReference<Descriptor<E>> descriptor;


    public LockFreeVector() {
        buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKETS);
        buckets.set(0,new AtomicReferenceArray<E>(FIRST_BUCKETS_SIZE));
        descriptor=new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,null));
    }

    public void push_back(E  e){
        Descriptor<E> desc;
        Descriptor<E> newd;

//        do{
//           desc=descriptor.get();
//           desc.completeWrite();
//
//           int pos = desc.size+FIRST_BUCKETS_SIZE;
//           int zeroNumPos = Integer.numberOfLeadingZeros(pos);
////           int bucketInd =
//        }while (1==1){
//
//        };
    }

    /**
     * 为了更有序的读写数组,定义一个称Descripptor的元素. 它的作用是使用CAS操作写入数据
     * @param <E>
     */
    static class Descriptor<E>{
        public int size;

        volatile WriteDescriptor<E> writeOp;

        public Descriptor(int size,WriteDescriptor<E> writeOp){
            this.size=size;
            this.writeOp=writeOp;
        }

        public void completeWrite(){
            WriteDescriptor<E> tmpOp=this.writeOp;
            if(tmpOp !=null){
                tmpOp.doIt();
                writeOp=null;
            }
        }

    }

    static class WriteDescriptor<E>{
        public E oldV;

        public E newV;

        public AtomicReferenceArray<E> addr;

        public int addr_ind;

        public WriteDescriptor(AtomicReferenceArray<E> addr,int addr_ind,E oldV,E newV){
            this.oldV=oldV;
            this.newV=newV;
            this.addr=addr;
            this.addr_ind=addr_ind;
        }

        public void doIt(){
            addr.compareAndSet(addr_ind,oldV,newV);
        }

    }
}
