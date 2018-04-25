package com.wfy.server.Test.doc.java8.vo;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: User.java, v 0.1 2018/1/19 12:14 fuck Exp $$
 */
public class User {

    private final int id;

    private final String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
