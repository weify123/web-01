package com.wfy.server.Test.doc;

import java.util.concurrent.*;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: RejectedThreadPoolDemo.java, v 0.1 2018/1/5 10:57 fuck Exp $$
 */
public class RejectedThreadPoolDemo {

    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.print(
                System.currentTimeMillis() + ":Thread Id:" + Thread.currentThread().getId() + "\n");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException{
        MyTask task = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<Runnable>(10), Executors.defaultThreadFactory(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    System.out.print(r.toString() + " is discard\n");
                }
            });

        for (int i = 0; i < Integer.MAX_VALUE; i++){
            es.submit(task);
            Thread.sleep(10);
        }
    }
}
