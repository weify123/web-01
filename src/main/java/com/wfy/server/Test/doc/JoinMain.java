package com.wfy.server.Test.doc;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: JoinMain.java, v 0.1 2018/1/3 15:49 fuck Exp $$
 */
public class JoinMain {

    public volatile static int i = 0;

    public static class AddThread extends Thread{
        @Override
        public void run() {
            for (i =0 ; i < 100000; i++);
        }
    }

    public static void main(String[] args) throws InterruptedException{
        AddThread at = new AddThread();
        at.start();
        at.join();
        System.out.print(i);
    }
}
