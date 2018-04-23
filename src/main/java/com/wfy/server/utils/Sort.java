package com.wfy.server.utils;

import java.util.Arrays;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Sort.java, v 0.1 2018/3/30 10:01 fuck Exp $$
 */
public class Sort {

    /**
     * 冒泡排序 稳定
     原理：相邻两个元素比较大小进行交换，一趟冒泡后会有一个元素到达最终位置
     复杂度：最好 - 最坏 - 平均 O(n) - O(n^2) - O(n^2)
     * @param a
     */
    public static void bubbleSort(int[] a) {
        if (null == a || a.length < 2) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            // 设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            boolean flag = true;
            for (int j = 0; j < a.length - i; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    /**
     * 插入排序 稳定
     原理：从有序序列中选择合适的位置进行插入
     复杂度：最好 - 最坏 - 平均 O(n) - O(n^2) - O(n^2)
     * @param a
     */
    public static void insertSort(int[] a) {
        if (null == a || a.length < 2) {
            return;
        }
        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        for (int i = 1; i < a.length; i++) {
            // 记录要插入的数据
            int temp = a[i];
            // 从已经排序的序列最右边的开始比较，找到比其小的数
            int j = i;
            while (j > 0 && temp < a[j - 1]) {
                a[j] = a[j - 1];
                j--;
            }
            // 存在比其小的数，插入
            if (j != i) {
                a[j] = temp;
            }
        }
    }

    /**
     * 希尔排序(缩小增量排序) 不稳定
     按步长进行分组，组内直接插入，缩小增量再次进行此步骤，增量为1时相当于一次直接插入。
     复杂度：最好O(n) - 最坏O(n^s 1<s<2) - 平均O(n^1.3)
     * @param a
     */
    public static void shellSort(int[] a) {
        if (null == a || a.length < 2) {
            return;
        }
        for (int d = a.length / 2; d > 0; d /= 2) {
            for (int i = d; i < a.length; i++) {
                // 内部直接插入
                int temp = a[i];
                int j = i - d;
                while (j >= 0 && temp < a[j]) {
                    a[j + d] = a[j];
                    j -= d;
                }
                a[j + d] = temp;
            }
        }
    }

    public static void shellSort1(int[] a) {
        if (null == a || a.length < 2) {
            return;
        }
        int gap = 1;
        while (gap < a.length) {
            gap = gap * 3 + 1;
        }

        while (gap > 0) {
            for (int i = gap; i < a.length; i++) {
                int tmp = a[i];
                int j = i - gap;
                while (j >= 0 && a[j] > tmp) {
                    a[j + gap] = a[j];
                    j -= gap;
                }
                a[j + gap] = tmp;
            }
            gap = (int) Math.floor(gap / 3);
        }
    }

    /**
     * 选择排序 不稳定
     原理：每次从无序序列选择一个最小的
     复杂度：最好O(n^2) - 最坏O(n^2) - 平均O(n^2)
     * @param a
     */
    public static void selectSort(int[] a) {
        if (null == a || a.length < 2) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            int k = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[k]) {
                    k = j;
                }
            }
            if (k != i) {
                int temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
        }
    }

    /**
     * 快速排序 不稳定
     原理：分治+递归
     复杂度：最好O(nlgn) - 最坏O(n^2) - 平均O(nlgn)
     * @param a
     * @param low
     * @param high
     */
    public static void quickSort(int[] a, int low, int high) {

        if (low < high) {
            int mid = partition(a, low, high);
            quickSort(a, low, mid - 1);
            quickSort(a, mid + 1, high);
        }
    }

    /**
     * 选取pivot的方式：固定基准元 随机基准 三数取中
     快排的优化：针对随机数组+有序数组+重复数组
     1.当待排序序列的长度分割到一定大小后，使用插入排序<三数取中+插入排序>：效率提高一些，但是都解决不了重复数组的问题。
     2.在一次分割结束后，可以把与Key相等的元素聚在一起，继续下次分割时，不用再对与key相等元素分割 <三数取中+插排+聚集相同元素>
     * @param a
     * @param low
     * @param high
     * @return
     */
    private static int partition(int[] a, int low, int high) {
        int pivot = a[low];

        while (low < high) {
            while (low < high && a[high] >= pivot) {
                high--;
            }
            a[low] = a[high];
            while (low < high && a[low] <= pivot) {
                low++;
            }
            a[high] = a[low];
        }
        a[low] = pivot;

        return low;
    }

    /**
     * 归并排序 稳定
     原理：两个有序序列的合并，方法：分治 + 递归
     复杂度：最好O(nlgn) - 最坏O(nlgn) - 平均O(nlgn)
     * @param a
     * @param low
     * @param high
     */
    public static void mergeSort(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            //左边
            mergeSort(a, low, mid);
            //右边
            mergeSort(a, mid + 1, high);
            //有序序列归并
            merge(a, low, mid, high);
        }
    }

    private static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        // 左指针
        int i = low;
        // 右指针
        int j = mid + 1;
        // 临时数组指针
        int k = 0;
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        //左边剩余
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        //右边剩余
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 倒出
        for (int t = 0; t < temp.length; t++) {
            a[t + low] = temp[t];
        }
    }

    /**
     * 堆排序
     原理：利用堆的特性
     复杂度：O(nlogn) [平均 - 最好 - 最坏]
     * @param a
     */
    public static void heapSort(int[] a) {
        if (null == a || a.length < 2) {
            return;
        }
        buildMaxHeap(a);
        for (int i = a.length - 1; i >= 0; i--) {
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            adjustHeap(a, i, 0);
        }
    }

    // 建堆
    private static void buildMaxHeap(int[] a) {
        for (int i = a.length/2; i >= 0; i--) {
            adjustHeap(a, a.length, i);
        }
    }

    // 调整堆
    private static void adjustHeap(int[] a, int size, int parent) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        int largest = parent;
        if (left < size && a[left] > a[largest]) {
            largest = left;
        }
        if (right < size && a[right] > a[largest]) {
            largest = right;
        }
        if (parent != largest) {
            int temp = a[parent];
            a[parent] = a[largest];
            a[largest] = temp;
            adjustHeap(a, size, largest);
        }
    }
    public static void main(String[] args) {
        int[] a = { 32, 12, 3, 44, 2, 6, 8 };
        bubbleSort(a);
        System.out.println(Arrays.toString(a));
        int[] s = { 32, 12, 3, 44, 2, 6, 8 };
        insertSort(s);
        System.out.println(Arrays.toString(s));
        int[] ss = { 32, 12, 3, 44, 2, 6, 8 };
        shellSort(ss);
        System.out.println(Arrays.toString(ss));
        int[] ss1 = { 32, 12, 3, 44, 2, 6, 8 };
        shellSort1(ss1);
        System.out.println(Arrays.toString(ss1));
        int[] sl = { 32, 12, 3, 44, 2, 6, 8 };
        selectSort(sl);
        System.out.println(Arrays.toString(sl));
        int[] qs = { 32, 12, 3, 44, 2, 6, 8 };
        quickSort(qs, 0, qs.length - 1);
        System.out.println(Arrays.toString(qs));
        int[] ms = { 32, 12, 3, 44, 2, 6, 8 };
        mergeSort(ms, 0, qs.length - 1);
        System.out.println(Arrays.toString(ms));
        int[] hs = { 32, 12, 3, 44, 2, 6, 8 };
        heapSort(hs);
        System.out.println(Arrays.toString(hs));

    }
}
