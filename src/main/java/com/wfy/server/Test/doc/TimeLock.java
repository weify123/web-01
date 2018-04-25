package com.wfy.server.Test.doc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: TimeLock.java, v 0.1 2018/1/4 14:02 fuck Exp $$
 */
public class TimeLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);
            }else{
                System.out.print(Thread.currentThread().getId() + " get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args){
        TimeLock timeLock = new TimeLock();
        Thread t1 = new Thread(timeLock);
        Thread t2 = new Thread(timeLock);
        t1.start();
        t2.start();
    }
}
