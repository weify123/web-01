package com.wfy.server.Test.doc.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: AskThread.java, v 0.1 2018/1/19 14:15 fuck Exp $$
 */
public class AskThread implements Runnable {

    CompletableFuture<Integer> re = null;

    public AskThread(CompletableFuture<Integer> re) {
        this.re = re;
    }

    @Override
    public void run() {
        int myRe = 0;
        try {
            myRe = re.get() * re.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(myRe);
    }

    public static void main(String[] args) throws InterruptedException{
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new AskThread(future)).start();
        // 模拟长时间的计算过程
        Thread.sleep(1000);
        // 告知完成结果
        future.complete(60);
    }
}
