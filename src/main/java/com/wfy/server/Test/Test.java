package com.wfy.server.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Test.java, v 0.1 2017/12/23 12:08 fuck Exp $$
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);

        CompletableFuture.runAsync(() -> {

        });

        final CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "30";
        }, service);

        //completableFuture.complete("1");
        CompletableFuture accept = completableFuture.thenApply(Integer::parseInt)
            .thenApply(r -> r * r * Math.PI);
        try {
            //System.out.print(accept.thenAcceptAsync(x -> logger.info("a:{}",x)) + "\n");
            accept.exceptionally(ex -> "ss:" + ex.toString());
            System.out.print(completableFuture.get() + "\n");
            System.out.print(accept.join() + "\n");
            System.out.print(accept.get() + "\n");

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }
}
