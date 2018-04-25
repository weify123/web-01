package com.wfy.server.Test.doc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: SemapDemo.java, v 0.1 2018/1/4 15:32 fuck Exp $$
 */
public class SemapDemo implements Runnable {

    final Semaphore semp = new Semaphore(5);

    @Override
    public void run() {
        try {
            semp.acquire();
            // 模拟耗时
            Thread.sleep(2000);
            System.out.print(Thread.currentThread().getId() + ":done!\n");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ExecutorService exec = Executors.newFixedThreadPool(20);

        final SemapDemo demo = new SemapDemo();
        for (int i = 0; i < 20; i++){
            exec.submit(demo);
        }

        try {
            // 询问谁执行结束了
            exec.shutdown();
            System.out.print("询问是否结束," + exec.isShutdown());
            // 三分钟还未结束直接关闭
            // (所有的任务都结束的时候，返回TRUE)
            if (!exec.awaitTermination(3, TimeUnit.MINUTES)) {
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                System.out.print("向线程池中所有的线程发出中断," + exec.isShutdown());
                exec.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.print("awaitTermination interrupted: " + e);
            exec.shutdownNow();
        }
    }
}
