package com.wfy.server.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * <p>
 * 当我们定义基本类型的 final 常量或者 String 类型的 final 常量时（只要为 final，不限制有无 static），
 * 如果在编译时能确定其确切值则编译器会将其用到的地方用其实际值进行替换，
 * 譬如 static final int A = 23; println(A); if(i > A){} 这样的语句会被编译成 static final int A = 23;
 * println(23); if(i > 23){} 的形式，所以即便运行时反射成功也没有任何意义，
 * 因为相关值已经在编译时被替换为了常量，而对于包装类型则没事
 * </p>;
 *
 * @author weifeiyu
 * @version Id: Invoke1.java, v 0.1 2018/4/25 10:05 fuck Exp $$
 */
public class Invoke1 {
    private static final int KEY_EXIT = 1024;
    private static int KEY_BACK = 1025;
    private static final String KEY_STR = "1001";
    private static void invok1() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("invok1->"+Invoke1.KEY_EXIT);
        Field field = Invoke1.class.getDeclaredField("KEY_EXIT");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, 256);
        System.out.println("invok1-<"+Invoke1.KEY_EXIT);
    }
    private static void invok2() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("invok2->"+Invoke1.KEY_BACK);
        Field field = Invoke1.class.getDeclaredField("KEY_BACK");
        field.setAccessible(true);
        field.set(null, 1000);
        System.out.println("invok2-<"+Invoke1.KEY_BACK);
    }
    private static void invok3() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("invok3->"+Invoke1.KEY_STR);
        Field field = Invoke1.class.getDeclaredField("KEY_STR");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, "512");
        System.out.println("invok3-<"+Invoke1.KEY_STR);
    }
    public static void main(String[] args) throws Exception {
        invok1();
        invok2();
        invok3();
    }
}
