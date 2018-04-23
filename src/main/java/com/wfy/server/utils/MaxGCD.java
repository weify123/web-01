package com.wfy.server.utils;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: MaxGCD.java, v 0.1 2018/4/17 17:00 fuck Exp $$
 */
public class MaxGCD {

    public static int gcd(int a, int b) {
        if (a == b) {
            return a;
        }
        if (a < b) {
            return gcd(b, a);
        } else {
            // 和1 按位与，判断奇偶
            if ((a & 1) == 0 && (b & 1) == 0) {
                return gcd(a >> 1, b >> 1);
            } else if ((a & 1) == 0 && (b & 1) != 0) {
                return gcd(a >> 1, b);
            } else if ((a & 1) != 0 && (b & 1) == 0) {
                return gcd(a, b >> 1);
            } else {
                return gcd(a, a - b);
            }
        }
    }
    public static void main(String[] args){
        System.out.println(gcd(10,20));
    }
}
