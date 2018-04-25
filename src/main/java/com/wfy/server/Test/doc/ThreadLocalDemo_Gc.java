package com.wfy.server.Test.doc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ThreadLocalDemo_Gc.java, v 0.1 2018/1/5 18:35 fuck Exp $$
 */
public class ThreadLocalDemo_Gc {

    static volatile ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.print(this.toString() + " is gc \n");
        }
    };
    static volatile CountDownLatch cd = new CountDownLatch(10000);

    public static class ParseDate implements Runnable{
        int i = 0;
        public ParseDate(int i){
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (local.get() == null){
                    local.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
                        @Override
                        protected void finalize() throws Throwable {
                            super.finalize();
                            System.out.print(this.toString() + " is gc \n");
                        }
                    });
                    System.out.print(Thread.currentThread().getId() + " :create SimpleDate \n");
                }
                Date t  = local.get().parse("2018-01-05 19:29:" + i % 60);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cd.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++){
            es.execute(new ParseDate(i));
        }
        cd.await();
        System.out.print("mission complete \n");
        local = null;
        System.gc();
        System.out.print("first gc complete\n");
        // 在设置ThreadLocal的时候，会清除ThreadLocalMap中无效的对象
        local = new ThreadLocal<SimpleDateFormat>();
        cd = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++){
            es.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.print("second gc complete\n");

    }

}
