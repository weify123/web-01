package com.wfy.server.Test.doc.java8.vo;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Student.java, v 0.1 2018/1/19 12:42 fuck Exp $$
 */
public class Student {
    private final String id;

    private final String name;

    private final int score;

    public Student(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
