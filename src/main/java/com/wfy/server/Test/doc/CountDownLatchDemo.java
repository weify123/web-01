package com.wfy.server.Test.doc;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: CountDownLatchDemo.java, v 0.1 2018/1/4 16:08 fuck Exp $$
 */
public class CountDownLatchDemo implements Runnable {
    static final CountDownLatch end = new CountDownLatch(10);
    static final CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            // 模拟检查任务
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.print("check complete\n");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++){
            exec.submit(demo);
        }
        // 等待检查
        end.await();
        System.out.print("Fire!");
        exec.shutdown();
    }
}
