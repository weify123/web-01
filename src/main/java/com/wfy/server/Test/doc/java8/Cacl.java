package com.wfy.server.Test.doc.java8;

import java.util.concurrent.CompletableFuture;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Cacl.java, v 0.1 2018/1/19 14:22 fuck Exp $$
 */
public class Cacl {

    public static Integer cacl(Integer para){
        try {
            // 模拟长时执行
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para;
    }

    public static Integer divide2(Integer para){
        return para / 2;
    }

    public static void main(String[] args) throws Exception{
        final CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> cacl(50));
        System.out.println("-------------");
        System.out.println(future.get());

        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> cacl(50))
                .thenApply(i -> Integer.toString(i))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        // 等待cacl()执行完成，若不等，随着主线程的执行结束，会立即退出，导致cacl无法正常执行
        future1.get();

        CompletableFuture f = CompletableFuture.supplyAsync(() -> divide2(50))
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> divide2(i)))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        f.get();

        CompletableFuture<Integer> intfuture = CompletableFuture.supplyAsync(() -> divide2(50));
        CompletableFuture<Integer> intfuture2 = CompletableFuture.supplyAsync(() -> divide2(25));
        CompletableFuture<Void> future2 = intfuture.thenCombine(intfuture2, (i, j) -> (i + j))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        future2.get();
    }
}
