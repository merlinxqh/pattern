package com.xqh.pattern.partterns.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncExecutor executor = new ThreadAsyncExecutor();

        AsyncResult<String> r1 = executor.startProcess(lazyval("test",1000l));
        AsyncResult<Integer> r2 = executor.startProcess(lazyval(100,2000L),callBack("int callback"));

        String res1 = executor.endProcess(r1);
        Integer res2 = executor.endProcess(r2);
        System.out.println(res1);
        System.out.println(res2);

    }

    private static <T> Callable<T> lazyval(T value, long delayMillis){
        return ()-> {
          Thread.sleep(delayMillis);
          log("Task completed with:"+ value);
          return value;
        };
    }

    private static <T> AsyncCallBack<T> callBack(String name){
        return (value, ex)-> {
            if(ex.isPresent()){
                log(name + " failed: "+ex.map(Exception::getMessage).orElse(""));
            }else{
                log(name + ":" + value);
            }
        };
    }

    private static void log(String msg){
        LOGGER.info(msg);
    }
}
