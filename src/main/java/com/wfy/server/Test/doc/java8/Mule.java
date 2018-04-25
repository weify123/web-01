package com.wfy.server.Test.doc.java8;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Mule.java, v 0.1 2018/1/18 13:39 fuck Exp $$
 */
public class Mule implements IHorse,IAnimal {
    @Override
    public void eat() {
        System.out.println("Mule eat");
    }
    public static void main(String[] args){
        Mule m  = new Mule();
        m.eat();
        m.run();
        m.breath();
    }
}
