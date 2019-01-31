package com.xqh.study.pattern.partterns.async;

import java.util.concurrent.ExecutionException;

public interface AsyncResult<T> {
    /**
     * 返回异步方法调用 任务 状态 是否完成
     * @return
     */
    boolean isCompleted();

    /**
     * 获取 状态 已完成 异步方法调用 任务 返回结果
     * @return
     * @throws ExecutionException
     */
    T getValue() throws ExecutionException;

    /**
     * 阻塞当前线程 等待 异步任务 调用完成
     * @throws InterruptedException
     */
    void await() throws InterruptedException;
}
