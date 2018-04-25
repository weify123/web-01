package com.wfy.server.Test.doc;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: AccountingVol.java, v 0.1 2018/1/3 18:28 fuck Exp $$
 */
public class AccountingVol implements Runnable {
    static AccountingVol instance = new AccountingVol();
    static volatile int i = 0;

    public static void increase(){
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++){
            synchronized (instance){
                increase();
            }
            // 初始版本，结果错误
            /*increase();*/
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.print(i);
    }
}
