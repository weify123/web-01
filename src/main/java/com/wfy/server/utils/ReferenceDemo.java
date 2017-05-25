package com.wfy.server.utils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * Created by weifeiyu on 2017/5/25.
 */
public class ReferenceDemo {

    // 创建引用队列
    private ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
    public class MyObject {
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            // 被回收时输出
            System.out.println("MyObject is finalize called");
        }
        @Override
        public String toString() {
            return " I am  MyObject";
        }
    }
    public class CheckRefQueue implements Runnable {
        Reference<MyObject> obj = null;
        @Override
        public void run() {
            try {
                // 如果对象被回收则进入引用队列
                obj = (Reference<MyObject>) referenceQueue.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (obj != null) {
                System.out.println("Object for SoftReference is " + obj.get());
            }
        }
    }
    public void test() {
        // 创建强引用
        MyObject myObject = new MyObject();
        // 构造myObject对象的软引用 注册到 引用队列
        SoftReference<MyObject> softReference = new SoftReference<>(myObject, referenceQueue);
        CheckRefQueue checkRefQueue = new CheckRefQueue();
        Thread thread = new Thread(checkRefQueue);
        thread.start();
        // 删除强引用 对myObject对象的引用只剩下软引用
        myObject = null;
        System.gc();
        System.out.println("After GC: Soft Get = " + softReference.get());
        System.out.println("分配大块内存");
        // 分配一块强大的内存，强迫GC
        byte[] b = new byte[5*1024*963];
        System.out.println("After new byte[]:Soft Get = "+softReference.get());
        System.gc();
    }
    public static void main(String[] args) {
        ReferenceDemo referenceDemo = new ReferenceDemo();
        referenceDemo.test();
    }
}
