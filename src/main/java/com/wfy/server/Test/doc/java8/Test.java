package com.wfy.server.Test.doc.java8;

import com.wfy.server.Test.doc.java8.util.PrimeUtil;
import com.wfy.server.Test.doc.java8.vo.Student;
import com.wfy.server.Test.doc.java8.vo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Test.java, v 0.1 2018/1/19 12:07 fuck Exp $$
 */
public class Test {

    interface UserFactory<T extends User>{
        T create(int id, String name);
    }

    static UserFactory<User> uf = User::new;

    static int[] arr = {1,3,5,6,7,8,9,10};

    interface stuFactory<S extends Student>{
        S produce(String id, String name, int score);
    }
    static stuFactory<Student> stuf = Student::new;

    public static void main(String[] args){
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
        numbers.forEach((Integer value) -> System.out.println(value));

        final int num = 2;
        Function<Integer, Integer> stringConverter = (from) -> from * num;
        System.out.println(stringConverter.apply(3));

        List<User> users = new ArrayList<User>();
        for (int i = 1; i < 10; i ++){
            users.add(new User(i, "billy" + Integer.toString(i)));
        }
        users.stream().map(User::getName).forEach(System.out::println);

        List<User> users1 = new ArrayList<User>();
        for (int i = 1; i < 10; i ++){
            users.add(uf.create(i, "billy" + Integer.toString(i)));
        }
        users.stream().map(User::getName).forEach(System.out::println);


        Arrays.stream(arr).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });
        Arrays.stream(arr).forEach((final int x) -> {
            System.out.println(x);
        });

        IntConsumer out = System.out::println;
        IntConsumer in = System.out::println;
        Arrays.stream(arr).forEach(out.andThen(in));

        //long count = IntStream.range(1, 1000000).filter(PrimeUtil::isPrime).count();
        //System.out.println(count);
        long count1 = IntStream.range(1, 1000000).parallel().filter(PrimeUtil::isPrime).count();

        System.out.println(count1);

        List<Student> students = new ArrayList<>();
        for (int i = 1; i < 100; i ++){
            students.add(stuf.produce(Integer.toString(i), Integer.toHexString(i), i));
        }
        /*double avescore = students.stream().mapToInt(s -> s.getScore()).average().getAsDouble();
        System.out.println(avescore);*/
        double avescore = students.parallelStream().mapToInt(s -> s.getScore()).average().getAsDouble();
        System.out.println(avescore);

        students.stream().parallel().forEach(x -> {
            System.out.println(x);
        });
    }
}
