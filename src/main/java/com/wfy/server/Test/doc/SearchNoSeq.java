package com.wfy.server.Test.doc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: SearchNoSeq.java, v 0.1 2018/1/10 19:17 fuck Exp $$
 */
public class SearchNoSeq {
    static int[] arr;
    static ExecutorService pool = Executors.newCachedThreadPool();
    static final int THREAD_NUM = 2;
    static AtomicInteger result = new AtomicInteger(-1);

    public static int search(int searchValue, int beginPos, int endPos){
        int i = 0;
        for (i = beginPos; i < endPos; i++){
            if (result.get() >= 0) {
                return result.get();
            }
            System.out.println("待搜索的值:" + searchValue);
            if (arr[i] == searchValue){
                if (!result.compareAndSet(-1, i)){
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    public static class SearchTask implements Callable<Integer>{
        int begin, end, searchValue;

        public SearchTask(int begin, int end, int searchValue) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }

        @Override
        public Integer call() throws Exception {
            return search(searchValue, begin,end);
        }
    }

    public static int pSearch(int searchValue) throws InterruptedException,ExecutionException{
        int subArrSize = arr.length / THREAD_NUM +1;
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        for (int i = 0; i < arr.length; i += subArrSize){
            int end = i + subArrSize;
            if (end >= arr.length) end = arr.length;
            futures.add(pool.submit(new SearchTask(i, end, searchValue)));
        }
        for (Future<Integer> future : futures){
            if (future.get() > 0) return future.get();
        }
        return -1;
    }

    public static void main(String[] args){
        arr = new int[10];
        arr[0] = 10;arr[1] = 555;arr[2] = 344;arr[3] = 22;arr[4] = 0;
        arr[5] = 33;arr[6] = 666;arr[7] = 11;arr[8] = 4;arr[9] = 143;
        try {
            System.out.println("搜索的索引为:" + pSearch(22));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
}
