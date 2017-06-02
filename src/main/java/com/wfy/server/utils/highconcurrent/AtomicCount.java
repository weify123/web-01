package com.wfy.server.utils.highconcurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by weifeiyu on 2017/6/2.
 */
public class AtomicCount {

    public AtomicInteger count = new AtomicInteger(0);

    static class Job implements Runnable{
        private AtomicCount count;
        private CountDownLatch countDown;
        public Job(AtomicCount count,CountDownLatch
                countDown){
            this.count = count;
            this.countDown = countDown;
        }
        @Override
        public void run() {
            boolean isSuccess = false;
            while(!isSuccess){
                int countValue = count.count.get();
                isSuccess = count.count.compareAndSet(countValue, countValue + 1);
            }
            countDown.countDown();
        }
    }
    public static void main(String[] args)
            throws InterruptedException {

        CountDownLatch countDown = new CountDownLatch(1500);
        AtomicCount count = new AtomicCount();
        ExecutorService ex = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 1500; i ++){
            ex.execute(new Job(count,countDown));
        }
        countDown.await();
        System.out.println(count.count);
        ex.shutdown();
    }
}
