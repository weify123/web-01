package com.wfy.server.Test.doc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ReenterLock.java, v 0.1 2018/1/4 12:26 fuck Exp $$
 */
public class ReenterLock implements Runnable{

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++){
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ReenterLock tL = new ReenterLock();
        Thread t1 = new Thread(tL);
        Thread t2 = new Thread(tL);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.print(i);
    }
}
