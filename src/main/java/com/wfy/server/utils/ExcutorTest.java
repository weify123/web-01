package com.wfy.server.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by weifeiyu on 2017/5/17.
 */
public class ExcutorTest {

        public static void main(String[] args) {
            // 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
            ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

           // 将线程放入池中进行执行
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread() + "------ccc");
                }
            });
            // 使用延迟执行风格的方法
            pool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread() + "------bbb");
                }
            }, 1, TimeUnit.MILLISECONDS);

          pool.scheduleAtFixedRate(new Runnable() { // 不会等上次任务结束，固定周期
                @Override
                public void run() {
                    for(int i =0; i < 1000000; i++){
                        System.out.println(i);
                    }
                    System.out.println(Thread.currentThread() + "------aaa");
                }
            }, 1, 3, TimeUnit.SECONDS);


            /*pool.scheduleWithFixedDelay(new Runnable() { // 等上次任务结束，延迟周期时间
                @Override
                public void run() {
                    for(int i =0; i < 1000000; i++){
                        System.out.println(i);
                    }
                    System.out.println(Thread.currentThread() + "------ddd");
                }
            }, 1, 3, TimeUnit.SECONDS);*/

            pool.schedule(new Runnable() {
                public void run() {
                    pool.shutdown();
                }
            }, 30, TimeUnit.SECONDS);
        }


    }
