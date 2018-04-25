package com.wfy.server.Test.doc.java8;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: IHorse.java, v 0.1 2018/1/18 13:37 fuck Exp $$
 */
public interface IHorse {
    void eat();
    default void run(){
        System.out.println("hourse run");
    }
}
