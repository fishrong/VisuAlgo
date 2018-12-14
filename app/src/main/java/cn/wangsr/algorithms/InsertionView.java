package cn.wangsr.algorithms;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;



public class InsertionView extends View {
    public int[] a=getResources().getIntArray(R.array.initArray);
    public int min = -1;
    public int currentIndex = -1;
    public int finished = -1;
    private int temp = -1;
    public int h=1;
    private int iTemp = -1;
    public int minColor = 0xff666666;
    public static void show(InsertionView myView, int min, int currentIndex, int finished, Handler handler){
        myView.min = min;
        myView.currentIndex = currentIndex;
        myView.finished = finished;

        handler.sendEmptyMessage(123);
        if (InsertionActivity.runAuto) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            while (!InsertionActivity.nextStep) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            InsertionActivity.nextStep = false;
        }

    }
    public InsertionView(Context context) {
        super(context);
    }

    public InsertionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int N = a.length;
        if (min==-1) temp = -1;
        if (h!=1 && finished!=-1) iTemp++;
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(0xff33ccff);
        for (int i = 0; i < N; i++) {

            if(min!=-1) {
                if (i == min) paint.setColor(minColor);//灰色，最小值
                else if (i == currentIndex) paint.setColor(0xff00cc00);//绿色，当前元素
                else if (h==1) {
                    if (i > temp) paint.setColor(0xff33ccff);//蓝色，未完成
                    else paint.setColor(0xffff6600);//橙色
                }
                else paint.setColor(0xff33ccff);//蓝色，未完成
                if (finished!=-1){
                    temp = finished;

                    if (h==1) {
                        if (i > finished) paint.setColor(0xff33ccff);//蓝色
                        else paint.setColor(0xffff6600);//橙色
                    }
                    else if ((i-iTemp)%h==0){

                        if (i > finished) paint.setColor(0xff33ccff);//蓝色
                        else paint.setColor(0xffff6600);//橙色
                    }
                    else paint.setColor(0xff33ccff);//蓝色
                }

            }
            else {
                paint.setColor(0xff33ccff);//蓝色
            }

            canvas.drawRect((getWidth()-N*100)/2+i*100, 500-a[i]*50, (getWidth()-N*100)/2+80+100*i, 500, paint);
            paint.setColor(Color.WHITE);//绘数组值
            paint.setTextSize(40);
            paint.setFakeBoldText(true);
            canvas.drawText(a[i]+"",(getWidth()-N*100)/2+i*100+25,490,paint);
        }


    }

}
