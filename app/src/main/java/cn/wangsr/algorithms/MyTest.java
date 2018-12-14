package cn.wangsr.algorithms;

import java.util.Arrays;

/**
 * Created by wangsr on 2018/12/2
 */
public class MyTest {
    public static void sort(int[] a){
        sort(a,0,a.length-1);
    }
    public static void sort(int[] a,int lo,int hi){
        if (hi<=lo) return;
        int j = partition(a,lo,hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    public static int partition(int[] a,int lo,int hi){
        int i = lo,j = hi+1;
        int v = a[lo];
        while (true){
            while (a[++i]<v) if (i==hi) break;
            while (a[--j]>v) if (j==lo) break;
            if (j<=i) break;
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        int temp = a[lo];
        a[lo] = a[j];
        a[j] = temp;
        return j;

    }
    public static void main(String[] args) {
        int[] a = {5,7,4,9,3,6};
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
