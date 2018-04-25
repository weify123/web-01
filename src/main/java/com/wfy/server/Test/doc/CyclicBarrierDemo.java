package com.wfy.server.Test.doc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: CyclicBarrierDemo.java, v 0.1 2018/1/4 16:47 fuck Exp $$
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable{
        private String soldier;
        private final CyclicBarrier cyclic;
        Soldier(CyclicBarrier cyclic, String soldier){
            this.cyclic = cyclic;
            this.soldier = soldier;
        }

        @Override
        public void run() {
            try {
                // 等待所有士兵到齐
                cyclic.await();
                doWork();
                // 等待所有士兵完成任务
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork(){
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(soldier + ":完成任务\n");
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;
        public BarrierRun(boolean flag, int N){
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if (flag){
                System.out.print("司令:[士兵" + N + "个，完成任务\n");
            }else {
                System.out.print("司令:[士兵" + N + "个，集合完毕\n");
                flag = true;
            }
        }
    }

    public static void main(String[] args){
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag,N));
        // 设置屏障点，主要为了执行这个方法
        System.out.print("集合队伍!\n ");
        for (int i = 0; i < N; i++){
            System.out.print("士兵" + i + " 报道!\n");
            allSoldier[i] = new Thread(new Soldier(cyclic,"士兵" + i));
            allSoldier[i].start();
        }
    }
}
