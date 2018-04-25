package com.wfy.server.Test.doc;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: DaemDemo.java, v 0.1 2018/1/3 18:15 fuck Exp $$
 */
public class DaemDemo {
    public static class DaeomT extends Thread{
        @Override
        public void run() {
            while (true){
                System.out.print("I am alive \n");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main (String[] args) throws InterruptedException{
        Thread t = new DaeomT();
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);
    }
}
