package com.wfy.server.Test.doc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: HashMapMultiThread.java, v 0.1 2018/1/3 18:48 fuck Exp $$
 */
public class HashMapMultiThread {
    //static Map<String,String> map =  new HashMap<>(); // 用concurrentHashMap 替换
    static ConcurrentHashMap<String,String> map =  new ConcurrentHashMap<>();
    public static class AddThread implements Runnable{
        int start = 0;
        public AddThread(int start){
            this.start = start;
        }
        @Override
        public void run() {
            for (int i = start; i < 100000; i += 2){
                map.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }
    }

    public static void main (String[] args) throws InterruptedException{
        Thread t1 = new Thread(new HashMapMultiThread.AddThread(0));
        Thread t2 = new Thread(new HashMapMultiThread.AddThread(1));

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.print(map.size());
    }
}
