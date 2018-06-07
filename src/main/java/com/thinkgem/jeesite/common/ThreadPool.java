package com.thinkgem.jeesite.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private volatile static ExecutorService instance;

    public static ExecutorService getInstance() {

        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null) {
                    instance = Executors.newCachedThreadPool();
                }
            }
        }

        return instance;
    }
}
