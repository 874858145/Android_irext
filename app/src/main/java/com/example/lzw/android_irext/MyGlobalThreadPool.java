package com.example.lzw.android_irext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyGlobalThreadPool {
    private static ExecutorService globalThreadPool = Executors.newCachedThreadPool();

    private MyGlobalThreadPool(){

    }

    // 获取全局线程池
    public static ExecutorService getGlobalThreadPool() {
        return globalThreadPool;
    }
}
