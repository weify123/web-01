package com.wfy.server.Test.doc;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: PriorityDemo.java, v 0.1 2018/1/3 18:19 fuck Exp $$
 */
public class PriorityDemo {

    public static class HighPriority extends Thread{
        static int count = 0;

        @Override
        public void run() {
            while (true){
                synchronized (PriorityDemo.class){
                    count++;
                    if (count > 10000000){
                        System.out.print("HighPriority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread{
        static int count = 0;

        @Override
        public void run() {
            while (true){
                synchronized (PriorityDemo.class){
                    count++;
                    if (count > 10000000){
                        System.out.print("LowPriority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        Thread high = new HighPriority();
        LowPriority low = new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        high.start();
    }
}
