package com.wfy.server.Test.doc;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: AutomicReferenceDemo.java, v 0.1 2018/1/9 16:08 fuck Exp $$
 */
public class AutomicReferenceDemo {

    static AtomicReference<Integer> money = new AtomicReference<>();

    public static void main(String[] args){
        // 设置账户初始值小于20,虽然这是一个需要被充值的账户
        money.set(19);
        for (int i = 0; i < 3; i++){
            new Thread(){
                @Override
                public void run() {
                    while (true){
                        while (true){
                            Integer m = money.get();
                            if (m < 20){
                                if (money.compareAndSet(m, m + 20)){
                                    System.out.print("余额小于20元，充值成功，余额:" + money.get() + "元\n");
                                    break;
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                }
            }.start();

            // 消费
            new Thread(){
                @Override
                public void run() {
                    for (int i = 0; i < 100; i ++){
                        while (true){
                            Integer m = money.get();
                            if (m > 10){
                                System.out.print("大于10元\n");
                                if (money.compareAndSet(m, m -10)){
                                    System.out.print("成功消费10元，余额:" + money.get() + "\n");
                                    break;
                                }else {
                                    System.out.print("没有足够金额\n");
                                }
                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
        }
    }


}
