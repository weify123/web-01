package com.wfy.server.Test.doc;

import java.util.concurrent.*;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ThreadFactoryDemo.java, v 0.1 2018/1/5 12:40 fuck Exp $$
 */
public class ThreadFactoryDemo {

    public static void main(String[] args){
        ExecutorService es = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        System.out.print("create:" + t + "\n");
                        return t;
                    }
                });
        for (int i = 0; i < 5; i++){
            es.submit(() -> {
                System.out.print(
                        System.currentTimeMillis() + ":Thread Id:" + Thread.currentThread().getId() + "\n");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
