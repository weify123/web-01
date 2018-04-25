package com.wfy.server.Test.doc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ScheduledExecutorServiceDemo.java, v 0.1 2018/1/5 10:19 fuck Exp $$
 */
public class ScheduledExecutorServiceDemo {

    public static void main(String[] args){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            try {
                Thread.sleep(1000);
                System.out.print("scheduleAtFixedRate:" + System.currentTimeMillis()/1000 + "\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },0, 2, TimeUnit.SECONDS);

        /*scheduledExecutorService.scheduleWithFixedDelay(()->{
            try {
                Thread.sleep(1000);
                System.out.print("scheduleWithFixedDelay:" + System.currentTimeMillis()/1000 + "\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },0, 2, TimeUnit.SECONDS);*/

        /*scheduledExecutorService.schedule(()->{
            try {
                Thread.sleep(2000);
                System.out.print("schedule:" + System.currentTimeMillis()/1000 + "\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },2, TimeUnit.SECONDS);*/
    }
}
