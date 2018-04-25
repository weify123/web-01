package com.wfy.server.Test.doc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: AtomicIntegerFieldUpdaterDemo.java, v 0.1 2018/1/9 17:13 fuck Exp $$
 */
public class AtomicIntegerFieldUpdaterDemo {
    public static class Candidate{
        int id;
        volatile int score;
    }
    public static AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
    // 检查Updater 是否正确工作
    public static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException{
        final Candidate candidate = new Candidate();
        Thread[] ts = new Thread[10000];
        for (int i = 0; i < 10000; i ++){
            ts[i] = new Thread(){
                @Override
                public void run() {
                    if (Math.random() > 0.4){
                        scoreUpdater.incrementAndGet(candidate);
                        allScore.incrementAndGet();
                    }
                }
            };
            ts[i].start();
        }

        for (int i = 0; i < 10000; i ++){
            ts[i].join();
        }
        System.out.print("score:" + candidate.score + "\n");
        System.out.print("allScore:" + allScore + "\n");
    }
}
