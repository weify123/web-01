package com.wfy.server.Test.doc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: FairLock.java, v 0.1 2018/1/4 14:10 fuck Exp $$
 */
public class FairLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true){
            try {
                lock.tryLock();
                System.out.print(Thread.currentThread().getName() + " 获得锁\n");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args){
        FairLock fairLock = new FairLock();
        Thread t1 = new Thread(fairLock,"Fair_Thread_1");
        Thread t2 = new Thread(fairLock,"Fair_Thread_2");
        t1.start();t2.start();
    }
}
