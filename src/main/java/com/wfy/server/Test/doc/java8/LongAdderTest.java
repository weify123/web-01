package com.wfy.server.Test.doc.java8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: LongAdderTest.java, v 0.1 2018/1/19 16:56 fuck Exp $$
 */
public class LongAdderTest {
    // 线程数
    private static final int MAX_THREADS = 3;
    // 任务数
    private static final int TASK_COUNT = 3;
    // 目标总数
    private static final int TARGET_COUNT = 3;

    private static AtomicLong atomicLong = new AtomicLong(0L);
    private static LongAdder longAdder = new LongAdder();
    private static long count = 0;

    static CountDownLatch taskCountSync = new CountDownLatch(TASK_COUNT);
    static CountDownLatch taskCountAtomic = new CountDownLatch(TASK_COUNT);
    static CountDownLatch taskCountAddr = new CountDownLatch(TASK_COUNT);

    protected static synchronized long inc(){
        return ++count;
    }

    protected static synchronized long getCount(){
        return count;
    }

    public static class SyncThread implements Runnable{
        protected long startTime;

        public SyncThread(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long v = getCount();
            while (v < TARGET_COUNT){
                v = inc();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("SyncThread spend:" + (endTime - startTime) + "ms" + "v=" + v);
            taskCountSync.countDown();
        }
    }

    public static class AtomicThread implements Runnable{
        protected long startTime;

        public AtomicThread(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long v = atomicLong.get();
            while (v < TARGET_COUNT){
                v = atomicLong.incrementAndGet();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("AtomicThread spend:" + (endTime - startTime) + "ms" + "v=" + v);
            taskCountAtomic.countDown();
        }
    }

    public static class LongAdderThread implements Runnable{
        protected long startTime;

        public LongAdderThread(long startTime) {
            this.startTime = startTime;
        }

        @Override
        public void run() {
            long v = longAdder.sum();
            while (v < TARGET_COUNT){
                longAdder.increment();
                v = longAdder.sum();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("LongAdderThread spend:" + (endTime - startTime) + "ms" + "v=" + v);
            taskCountAddr.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime = System.currentTimeMillis();
        SyncThread sync = new SyncThread(startTime);
        for (int i = 0; i < TASK_COUNT; i++){
            service.submit(sync);
        }
        taskCountSync.await();
        service.shutdown();

        ExecutorService service1 = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime1 = System.currentTimeMillis();
        AtomicThread atomicThread = new AtomicThread(startTime1);
        for (int i = 0; i < TASK_COUNT; i++){
            service1.submit(atomicThread);
        }
        taskCountAtomic.await();
        service1.shutdown();

        ExecutorService service2 = Executors.newFixedThreadPool(MAX_THREADS);
        long startTime2 = System.currentTimeMillis();
        LongAdderThread LongAdderThread = new LongAdderThread(startTime2);
        for (int i = 0; i < TASK_COUNT; i++){
            service2.submit(LongAdderThread);
        }
        taskCountAddr.await();
        service2.shutdown();
    }
}
