package com.wfy.server.Test.doc;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: DivTask.java, v 0.1 2018/1/5 14:19 fuck Exp $$
 */
public class DivTask implements Runnable {
    int a,b;
    public DivTask(int a, int b){
        this.a=a;
        this.b=b;
    }

    @Override
    public void run() {
        double re = a/b;
        System.out.print(re + "\n");
    }

    public static void main(String[] args){
        // 1、直接使用工具类
        ThreadPoolExecutor pools = new ThreadPoolExecutor(0,Integer.MAX_VALUE,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());

        for (int i = 0; i < 5; i++){
            // pools.submit(new DivTask(100,i)); 不会打印错误日志，只有用get 获取才会
            pools.execute(new DivTask(100,i));
        }
        // 2、使用扩展
        ThreadPoolExecutor traceThreadPoolExecutor = new TraceThreadPoolExecutor(0,Integer.MAX_VALUE,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>());

        for (int i = 0; i < 5; i++){
            traceThreadPoolExecutor.submit((new DivTask(100,i)));
        }
    }
}
