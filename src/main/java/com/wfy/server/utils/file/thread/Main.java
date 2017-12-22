package com.wfy.server.utils.file.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Main.java, v 0.1 2017/12/21 21:20 fuck Exp $$
 */
public class Main {

    static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        BigFileReader.Builder builder = new BigFileReader.Builder("E:/Weihui/fund_netValue/fund_nav.txt",new IHandle() {

            @Override
            public void handle(String line) {
                //lock.lock();
                try {
                    System.out.println(line);
                    Thread.sleep(11111111);
                    System.out.print("--:" + Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //lock.unlock();

            }
        });
        builder.withTreahdSize(10)
                .withCharset("gbk")
                .withBufferSize(1024*1024);
        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();

        //bigFileReader.shutdown();
    }
}
