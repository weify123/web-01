package com.wfy.server.Test.doc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ReenterLockCondition.java, v 0.1 2018/1/4 15:22 fuck Exp $$
 */
public class ReenterLockCondition implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {

        try {
            lock.lock();
            condition.await();
            System.out.print("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ReenterLockCondition lockCondition = new ReenterLockCondition();
        Thread  t1 = new Thread(lockCondition);
        t1.start();
        Thread.sleep(2000);
        // 通知t1继续执行
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
