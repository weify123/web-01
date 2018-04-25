package com.wfy.server.Test.doc;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: ThreadGroupName.java, v 0.1 2018/1/3 18:05 fuck Exp $$
 */
public class ThreadGroupName implements Runnable{

    public static void main(String[] args){
        ThreadGroup tg = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(tg,new ThreadGroupName(), "T1");
        Thread t2 = new Thread(tg,new ThreadGroupName(), "T2");
        t1.start();
        t2.start();
        System.out.print(tg.activeCount());
        tg.list();
    }

    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName();
        while (true){
            System.out.print("I am " + groupAndName + "\n");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
