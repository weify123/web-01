package com.wfy.server.Test.doc.java8;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Test2.java, v 0.1 2018/1/19 12:55 fuck Exp $$
 */
public class Test2 {

    public static void main(String[] args){
        int[] arr = new int[10000000];
        Random r = new Random();
       /* Arrays.setAll(arr, i -> r.nextInt());
        Arrays.stream(arr).forEach(x ->{
            System.out.println(x);
        });
        Arrays.sort(arr);
        Arrays.stream(arr).forEach(x ->{
            System.out.println(x);
        });*/

        Arrays.parallelSetAll(arr, i -> r.nextInt());
        Arrays.parallelSort(arr);
        Arrays.stream(arr)./*parallel().*/forEach(x ->{
            System.out.println(x);
        });
    }
}
