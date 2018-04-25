package com.wfy.server.Test.doc;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: InterruptTest.java, v 0.1 2018/1/3 13:49 fuck Exp $$
 */
public class InterruptTest {

    public static void main(String[] args) throws InterruptedException{

        Thread t = new Thread(){
            @Override
            public void run() {
                while (true){
                    if (Thread.currentThread().isInterrupted()){
                        System.out.print("Interupted!");
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.print("Interrupted when sleep ");
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }
}
