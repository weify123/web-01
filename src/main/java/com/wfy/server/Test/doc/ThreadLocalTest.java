package com.wfy.server.Test.doc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ThreadLocalTest.java, v 0.1 2018/1/5 19:06 fuck Exp $$
 */
public class ThreadLocalTest {

    public static final int GEN_COUNT = 10000000;
    public static final int THREAD_COUNT = 4;
    static ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);
    public static Random rnd = new Random(123);
    public static ThreadLocal<Random> tRnd = new ThreadLocal<Random>(){
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    public static class RndTask implements Callable<Long>{
        private int mode = 0;
        public RndTask(int mode){
            this.mode = mode;
        }

        public Random getRandom(){
            if (mode == 0){
                return rnd;
            }
            else if (mode == 1){
                return tRnd.get();
            }
            else {
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long b = System.currentTimeMillis();
            for (long i = 0; i < GEN_COUNT; i++){
                getRandom().nextInt();
            }
            long e = System.currentTimeMillis();
            System.out.print(Thread.currentThread().getName() + " send " + (e - b) + " ms\n");
            return e - b;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException{
        Future<Long>[] futs = new Future[THREAD_COUNT];
        for (int i = 0;  i < THREAD_COUNT; i++){
            futs[i] = es.submit(new RndTask(0));
        }
        long totalTime = 0;
        for (int i = 0; i < THREAD_COUNT; i++){
            totalTime += futs[i].get();
        }
        System.out.print("多线程访问同一个Random实例:" + totalTime + " ms\n");

        // threadLocal 的情况
        for (int i = 0;  i < THREAD_COUNT; i++){
            futs[i] = es.submit(new RndTask(1));
        }
        totalTime = 0;
        for (int i = 0; i < THREAD_COUNT; i++){
            totalTime += futs[i].get();
        }
        System.out.print("使用ThreadLocal包装Random实例:" + totalTime + " ms\n");
        es.shutdown();
    }

}
