package com.xqh.study.pattern.partterns.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public interface AsyncExecutor{

    /**
     * 开始 异步任务
     * @param task
     * @param <T>
     * @return
     */
    <T> AsyncResult<T> startProcess(Callable<T> task);

    /**
     * 开始异步任务  带 回调函数
     * @param task
     * @param callBack
     * @param <T>
     * @return
     */
    <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallBack<T> callBack);

    /**
     * 返回异步任务 结果
     * @param asyncResult
     * @param <T>
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    <T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException,InterruptedException;
}
