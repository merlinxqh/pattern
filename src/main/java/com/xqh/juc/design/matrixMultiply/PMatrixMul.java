package com.xqh.juc.design.matrixMultiply;

import com.alibaba.fastjson.JSON;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by leo on 2017/10/11.
 */
public class PMatrixMul {

    public static final int granularity=3;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        Matrix m1= MatrixFactory.getRandomMatrix(100,100,null);
        Matrix m2= MatrixFactory.getRandomMatrix(100,100,null);
        MatrixMulTask task=new MatrixMulTask(m1,m2,null);
        ForkJoinTask<Matrix> result=forkJoinPool.submit(task);
        Matrix pr=result.get();
        System.out.println("------------------------m1-----------------------");
        System.out.println(m1);
        System.out.println("------------------------m2-----------------------");
        System.out.println(m2);
        System.out.println("------------------------pr-----------------------");
        System.out.println(pr);
    }
}
