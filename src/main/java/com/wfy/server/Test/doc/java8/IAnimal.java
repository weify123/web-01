package com.wfy.server.Test.doc.java8;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: IAnimal.java, v 0.1 2018/1/18 13:38 fuck Exp $$
 */
public interface IAnimal {
    default void breath(){
        System.out.println("breath!");
    }
}
