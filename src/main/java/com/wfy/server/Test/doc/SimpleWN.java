package com.wfy.server.Test.doc;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: SimpleWN.java, v 0.1 2018/1/3 14:05 fuck Exp $$
 */
public class SimpleWN {

    final static Object object  = new Object();

    public static class T1 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                System.out.print(System.currentTimeMillis() + ":T1 start! ");
                try {
                    System.out.print(System.currentTimeMillis() + ":T1 wait for object ");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(System.currentTimeMillis() + ":T1 end! ");
            }
        }
    }

    public static class T2 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                System.out.print(System.currentTimeMillis() + ":T2 start! notify one thread ");
                object.notify();
                System.out.print(System.currentTimeMillis() + ":T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.print("interrupt " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args){
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }

}
