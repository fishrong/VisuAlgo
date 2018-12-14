package cn.wangsr.algorithms;

import android.util.Log;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit ll_btn_orange, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    public static void testQuik(){
        int[] a = {5,4,9,7,6,1};
        sort(a);
        Log.d(">>>>>>>>", "testQuik: "+ Arrays.toString(a));
    }
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
            while (a[++i]>v) if (i==hi) break;
            while (a[--j]<v) if (j==lo) break;
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

}