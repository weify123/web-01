package com.wfy.server.Test.doc;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: AtomicIntegerArrayDemo.java, v 0.1 2018/1/9 16:45 fuck Exp $$
 */
public class AtomicIntegerArrayDemo {
    static AtomicIntegerArray arr = new AtomicIntegerArray(10);
    public static class AddThread implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10000; i ++){
                arr.getAndIncrement(i % arr.length());
            }
        }
    }
    public static void main(String[] args) throws InterruptedException{
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++){
            ts[i] = new Thread(new AddThread());
        }
        for (int i = 0; i < 10; i++){
            ts[i].start();
        }
        for (int i = 0; i < 10; i++){
            ts[i].join();
        }
        System.out.print(arr);
    }
}
