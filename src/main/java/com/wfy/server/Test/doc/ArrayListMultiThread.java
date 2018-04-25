package com.wfy.server.Test.doc;

import java.util.ArrayList;
import java.util.Vector;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ArrayListMultiThread.java, v 0.1 2018/1/3 18:41 fuck Exp $$
 */
public class ArrayListMultiThread {
    //static ArrayList<Integer> list = new ArrayList<>();// 改进方法 list 改为vector
    static Vector<Integer> list = new Vector<>();// 改进方法 list 改为vector
    public static class AddThread implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 100000; i++){
                list.add(i);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.print(list.size());
    }
}
