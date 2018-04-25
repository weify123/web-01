package com.wfy.server.Test.doc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ThreadPoolDemo.java, v 0.1 2018/1/5 09:43 fuck Exp $$
 */
public class ThreadPoolDemo {
    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.print(
                System.currentTimeMillis() + ":Thread Id:" + Thread.currentThread().getId() + "\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        MyTask task = new MyTask();
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++){
            exec.submit(task);
        }
    }
}
