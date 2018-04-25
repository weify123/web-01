package com.wfy.server.Test.doc.java8;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: IntHandler.java, v 0.1 2018/1/18 13:26 fuck Exp $$
 */
@FunctionalInterface
public interface IntHandler {
    void handle(int i);

    boolean equals(Object obj);
}
