package com.wfy.server.Test.doc;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: MultiThreadLong.java, v 0.1 2018/1/2 13:51 fuck Exp $$
 */
public class MultiThreadLong {
    public static long t = 0;

    public static class ChangeT implements Runnable{
        private long to;
        public ChangeT(long to){
            this.to = to;
        }
        @Override
        public void run(){
            while (true){
                MultiThreadLong.t = to;
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable{
        @Override
        public void run(){
            while (true){
                long tmp = MultiThreadLong.t;
                if (tmp != 111L && tmp != -999L && tmp != 333L && tmp != -444L){
                    System.out.print(tmp);
                }
                Thread.yield();
            }
        }
    }
    public static void main(String[] args){
        new Thread(new ChangeT(111L)).start();
        new Thread(new ChangeT(-999L)).start();
        new Thread(new ChangeT(333L)).start();
        new Thread(new ChangeT(-444L)).start();
        new Thread(new ReadT()).start();
    }
}
