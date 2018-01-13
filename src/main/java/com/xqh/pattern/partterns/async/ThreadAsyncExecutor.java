package com.xqh.pattern.partterns.async;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadAsyncExecutor implements AsyncExecutor {

    private final AtomicInteger idx = new AtomicInteger(0);

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> task) {
        return startProcess(task,null);
    }

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallBack<T> callBack) {
        CompletableResult<T> result = new CompletableResult<>(callBack);
        new Thread(()-> {
            try {
                System.out.println("start process...");
                result.setValue(task.call());
            } catch (Exception e) {
                result.setException(e);
            }
        },"executor-"+idx.incrementAndGet()).start();
        return result;
    }

    @Override
    public <T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException, InterruptedException {
        if(!asyncResult.isCompleted()){
            asyncResult.await();
        }
        return asyncResult.getValue();
    }




    public static class CompletableResult<T> implements AsyncResult<T>{
        static final int RUNNING = 1;
        static final int FAILED = 2;
        static final int COMPLETED = 3;

        final Object lock;

        final Optional<AsyncCallBack<T>> callBack;

        volatile int state = RUNNING;

        T value;

        Exception exception;

        CompletableResult(AsyncCallBack<T> callBack){
            this.callBack = Optional.ofNullable(callBack);
            this.lock = new Object();
        }

        void setValue(T value){
            this.value = value;
            this.state = COMPLETED;
            this.callBack.ifPresent(ac-> ac.onCompleted(value, Optional.<Exception>empty()));
            synchronized (lock){
                lock.notifyAll();
            }
        }

        void setException(Exception exception){
            this.exception = exception;
            this.state = FAILED;
            this.callBack.ifPresent(ac-> ac.onCompleted(null, Optional.of(exception)));
            synchronized (lock){
                lock.notifyAll();
            }
        }

        @Override
        public boolean isCompleted() {
            return state > RUNNING;
        }

        @Override
        public T getValue() throws ExecutionException {
            if(state == COMPLETED){
                return value;
            }else if(state == FAILED){
                throw new ExecutionException(exception);
            } else {
                throw new IllegalStateException("Execution not completed yet");
            }
        }

        @Override
        public void await() throws InterruptedException {
            synchronized (lock){
                while (!isCompleted()){
                    lock.wait();
                }
            }
        }
    }
}
