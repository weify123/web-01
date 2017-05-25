package com.wfy.server.utils;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * Created by weifeiyu on 2017/5/25.
 */
public class PhantomReferenceDemo {
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
                // 等待 直到取得虚引用对象
                System.out.println("Object for PhantomReference is " + obj.get());
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (obj != null) {
                System.out.println("Object for SoftReference is " + obj.get());
            }
        }
    }
    public void test() throws InterruptedException {
        // 创建强引用
        MyObject myObject = new MyObject();
        // 构造myObject对象的虚引用 注册到 引用队列
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);
        System.out.println("phantomReference Get : " + phantomReference.get());
        CheckRefQueue checkRefQueue = new CheckRefQueue();
        Thread thread = new Thread(checkRefQueue);
        thread.start();
        // 删除强引用 对myObject对象的引用只剩下虚引用
        myObject = null;
        Thread.sleep(1000);
        int i = 1;
        while(true){
            System.out.println("第" + i + "次GC");
            System.gc();
            Thread.sleep(1000);
            i++;
        }//while
    }
    public static void main(String[] args) throws InterruptedException {
        PhantomReferenceDemo referenceDemo = new PhantomReferenceDemo();
        referenceDemo.test();
    }
}
