package cn.wangsr.algorithms;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by wangsr on 2018/12/11
 */
public class QuickView extends View {
    private int[] a = getResources().getIntArray(R.array.initArray);
    private int lo;
    private int hi;
    private int i,j;
    private String finished="";


    private boolean init;//初始化
    private Paint paint = new Paint();
    public QuickView(Context context) {
        super(context);
    }

    public QuickView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private void threadSleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断自动还是手动
     */
    private void myWait(){
        if (QuickActivity.runAuto) {
            threadSleep(1000);
        }
        else {
            while (!QuickActivity.nextStep) {
                threadSleep(100);
            }
            QuickActivity.nextStep = false;
        }
    }

    /**
     * 切分时显示
     * @param a
     * @param handler
     * @param i
     * @param j
     * @param lo
     * @param hi
     */
    public void show(int[] a, Handler handler, int i,int j,int lo, int hi){
        this.finished = "";
        this.init = true;
        this.a = a;
        this.lo = lo;
        this.hi = hi;
        this.i = i;
        this.j = j;
        handler.sendEmptyMessage(0x1234);
        myWait();
    }

    /**
     * 完成时显示
     * @param a
     * @param handler
     * @param lo
     * @param hi
     * @param finished
     */
    public void show(int[] a,Handler handler,int lo, int hi,String finished){
        this.finished = finished;
        this.lo = lo;
        this.hi = hi;
        this.a = a;
        handler.sendEmptyMessage(0x1234);
        myWait();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int N = a.length;
        for (int k = 0; k < N; k++) {
            if (finished.equals("sortFinished")){//排序完成
                paint.setColor(getResources().getColor(R.color.LightBlue));
            }
            else {

                if (finished.equals("partitionFinished")) {//切分完成
                    if (k >= lo && k <= hi )
                        paint.setColor(getResources().getColor(R.color.Blue));
                    else paint.setColor(getResources().getColor(R.color.LightBlue));
                } else {
                    if (init) {
                        if (k == lo) paint.setColor(getResources().getColor(R.color.Gray));
                        else if (k == i) paint.setColor(getResources().getColor(R.color.Green));
                        if (j <= hi && k == j)
                            paint.setColor(getResources().getColor(R.color.DarkOrange));
                        if ((k != i && k != j && k != lo) || k == hi+1) {
                            if (k >= lo && k <= hi)
                                paint.setColor(getResources().getColor(R.color.Blue));
                            else paint.setColor(getResources().getColor(R.color.LightBlue));
                        }

                    } else paint.setColor(getResources().getColor(R.color.Blue));//第一次调用
                }
            }




            canvas.drawRect((getWidth()-N*100)/2+k*100, 500-a[k]*50, (getWidth()-N*100)/2+80+100*k, 500, paint);
            paint.setColor(Color.WHITE);//绘数组值
            paint.setTextSize(40);
            paint.setFakeBoldText(true);
            canvas.drawText(a[k]+"",(getWidth()-N*100)/2+k*100+25,490,paint);
        }

    }
}
