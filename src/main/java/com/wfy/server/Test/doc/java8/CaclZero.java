package com.wfy.server.Test.doc.java8;

import java.util.concurrent.CompletableFuture;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: CaclZero.java, v 0.1 2018/1/19 14:35 fuck Exp $$
 */
public class CaclZero {

    public static Integer cacl(Integer para){
        return para / 0;
    }

    public static void main(String[] args) throws Exception{
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> cacl(50))
                .exceptionally(ex ->{
                    System.out.println(ex.toString());
                    return 0;
                })
                .thenApply(x -> Integer.toString(x))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        future.get();
    }
}
