package com.xqh.pattern.partterns.async;

import java.util.Optional;

/**
 * 异步方法调用 回调函数
 */
public interface AsyncCallBack<T> {

    /**
     * 异步任务调用完成 回调方法
     * @param value  异步任务 返回结果
     * @param ex
     */
    void onCompleted(T value, Optional<Exception> ex);

}
