package com.wfy.server.utils.thread.threadadd;

import java.util.concurrent.CountDownLatch;

/**
 * 使用countDownLatch
 * 子线程执行完调用 countdownlatch.countdown()，主线程调用countdownlatc.await() 等待子线程执行完成，输出结果。
 *  注意：sum为共享变量，访问共享变量时，用synchronized同步
 * @author wfy  2017年2月8日 下午2:51:20
 * @since JDK 1.8	
 */
public class ThreadAddLatch {

	 	public static int sum = 0;
	    public static Object LOCK = new Object();
	    public static CountDownLatch countdown = new CountDownLatch(4);

	    public static void main(String[] args) throws InterruptedException {
	        ThreadAddLatch add = new ThreadAddLatch();
	        ThreadTest thread1 = add.new ThreadTest(1, 25);
	        ThreadTest thread2 = add.new ThreadTest(26, 50);
	        ThreadTest thread3 = add.new ThreadTest(51, 75);
	        ThreadTest thread4 = add.new ThreadTest(76, 100);
	        thread1.start();
	        thread2.start();
	        thread3.start();
	        thread4.start();

	        countdown.await();
	        System.out.println("total result: "+sum);
	    }

	    class ThreadTest extends Thread {
	        private int begin;
	        private int end;

	        @Override
	        public void run() {
	            synchronized (LOCK) {
	                for (int i = begin; i <= end; i++) {
	                    sum += i;
	                }
	                System.out.println("from "+Thread.currentThread().getName()+" sum="+sum);
	            }
	            countdown.countDown();
	        }

	        public ThreadTest(int begin, int end) {
	            this.begin = begin;
	            this.end = end;
	        }
	    }
}
