package com.wfy.server.Test.doc;

import java.util.concurrent.locks.LockSupport;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: LockSupportDemo.java, v 0.1 2018/1/4 17:33 fuck Exp $$
 */
public class LockSupportIntDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.print("in " + getName() + "\n");
                LockSupport.park();
                if (Thread.interrupted()){
                    System.out.print(getName() + "被中断了\n");
                }
            }
            System.out.print(getName() + "执行结束!\n");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
