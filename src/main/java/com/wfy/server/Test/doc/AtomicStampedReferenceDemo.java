package com.wfy.server.Test.doc;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: AtomicStampedReferenceDemo.java, v 0.1 2018/1/9 16:08 fuck Exp $$
 */
public class AtomicStampedReferenceDemo {

    static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(19, 0);

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            final int timestamp = money.getStamp();
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        while (true) {
                            Integer m = money.getReference();
                            if (m < 20) {
                                if (money.compareAndSet(m, m + 20, timestamp, timestamp + 1)) {
                                    System.out.print("余额小于20元，充值成功，余额:" + money.getReference() + "元\n");
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }.start();

            // 消费
            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        while (true) {
                            Integer m = money.getReference();
                            final int timestamp = money.getStamp();
                            if (m > 10) {
                                System.out.print("大于10元\n");
                                if (money.compareAndSet(m, m - 10, timestamp, timestamp + 1)) {
                                    System.out.print("成功消费10元，余额:" + money.getReference() + "\n");
                                    break;
                                } else {
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
