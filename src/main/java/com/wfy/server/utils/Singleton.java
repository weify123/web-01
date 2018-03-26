package com.wfy.server.utils;

/**
 * <span>
 * 单例模式使用内部类来维护单例的实现，JVM内部的机制能够保证当一个类被加载的时候，
 * 这个类的加载过程是线程互斥的。这样当我们第一次调用getInstance的时候，
 * JVM能够帮我们保证instance只被创建一次，并且会保证把赋值给instance的内存初始化完毕.
 * 同时该方法也只会在第一次调用的时候使用互斥机制，
 * 这样就解决了低性能问题
 * </span>;
 *
 * @author weifeiyu
 * @version Id: Singleton.java, v 0.1 2018/3/26 10:40 fuck Exp $$
 */
public class Singleton {

    private Singleton() {

    }

    private static class SingletonFactory {
        private static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonFactory.instance;
    }

    public Object readResolve() {
        return getInstance();
    }
}
