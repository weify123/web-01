package com.wfy.server.utils;

/**
 * <p>通过位运算实现两个数的加、减、乘、除</p>;
 *
 * @author weifeiyu
 * @version Id: BitSuan.java, v 0.1 2018/4/4 17:36 fuck Exp $$
 */
public class BitSuan {

    //(1)加法运算
    public static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            //a与b无进位相加
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;

    }

    //负数按位置取反+1
    public static int negNum(int n) {
        return add(~n, 1);
    }

    //(2)减法运算
    public static int minus(int a, int b) {
        return add(a, negNum(b));
    }

    //(3)乘法运算
    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return res;
    }

    //判断是否是负数
    public static boolean isNeg(int n) {
        return n < 0;
    }

    public static int div(int a, int b) {
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;
        int res = 0;
        for (int i = 31; i > -1; i = minus(i, 1)) {
            if ((x >> i) >= y) {
                res |= (1 << i);
                x = minus(x, y << i);
            }
        }
        return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
    }

    //(4)除法运算
    public static int divide(int a, int b) {
        if (b == 0) {
            throw new RuntimeException("divisor is 0");
        }
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
        } else if (b == Integer.MIN_VALUE) {
            return 0;
        } else if (a == Integer.MIN_VALUE) {
            int res = div(add(a, 1), b);
            return add(res, div(minus(a, multi(res, b)), b));
        } else {
            return div(a, b);
        }
    }

    public static void main(String[] args) {
        int a = 10;
        int b = 5;
        System.out.println(add(a, b));
        System.out.println(minus(a, b));
        System.out.println(multi(a, b));
        System.out.println(divide(a, b));


        int x = 10, y = 20; //定义两个变量
        System.out.println("交换前 x=" + x + ",y=" + y);
        x = x + y; //x = 30
        y = x - y; //y = 10
        x = x - y; //x = 20
        System.out.println("交换后 x=" + x + ",y=" + y);

        System.out.println("交换前 x=" + x + ",y=" + y);
        x = x ^ y;  //x = 30
        y = x ^ y;  //y = 10
        x = x ^ y;  //x = 20
        System.out.println("交换后 x=" + x + ",y=" + y);
    }
}
