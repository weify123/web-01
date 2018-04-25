package com.wfy.server.Test.doc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ExtThreadPool.java, v 0.1 2018/1/5 13:52 fuck Exp $$
 */
public class ExtThreadPool {
    public static class MyTask implements Runnable{
        private String name;

        public MyTask(String name){
            this.name = name;
        }

        @Override
        public void run() {
            System.out.print("正在执行:Thread ID:" + Thread.currentThread().getId() + ",Task Name=" + name + "\n");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ExecutorService es  = new ThreadPoolExecutor(5,5,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.print("准备执行:" + ((MyTask)r).name + "\n");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.print("执行完成:" + ((MyTask)r).name + "\n");
            }

            @Override
            protected void terminated() {
                System.out.print("线程池退出" + "\n");
            }
        };

        for (int i = 0; i < 5; i++){
            MyTask task = new MyTask("TASK-GEYM-" + i);
            es.execute(task);
            Thread.sleep(10);
        }
        es.shutdown();

        // Ncpu = CPU数量
        //Ucpu = 目标CPU的使用率，0<=Ucpu<=1
        //W/C = 等待时间与计算时间的比率
        // 最优线程池大小
        // NThreads = Ncpu * Ucpu *(1 + W/C)

        System.out.print("CPU数量:" + Runtime.getRuntime().availableProcessors() + "\n");
    }
}
