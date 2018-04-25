package com.wfy.server.Test.doc.java8.util;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: PrimeUtil.java, v 0.1 2018/1/19 12:32 fuck Exp $$
 */
public class PrimeUtil {
    public static boolean isPrime(int number){
        int tmp = number;
        if (tmp < 2){
            return false;
        }
        for (int i = 2; Math.sqrt(tmp) >= i; i++){
            if (tmp % i == 0){
                return false;
            }
        }
        return true;
    }
}
