package cn.wangsr.algorithms;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


public class MergeView extends View {
    public int[] a=getResources().getIntArray(R.array.initArray);
    private  int lo,hi,k;
    public int mergeStart=-1;
    private static Handler handler;
    private  int[] aux = new int[a.length];
    public int[] aTemp = new int[a.length];
    public float x,y;//矩形横纵坐标调整
    public boolean animate;//开始动画
    private int xLength;//横坐标位移
    public boolean finished=true;//一次是否merge完成
    private int iTemp;//计算横坐标位移距离辅助位
    private Paint paint = new Paint();

    private void threadSleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void show(int[] a,int lo,int hi,Handler handler){
        this.mergeStart = 0;//第一次显示绿色标志
        this.lo = lo;
        this.hi = hi;
        System.arraycopy(a, 0, this.a, 0, a.length);//不可直接使用指针赋值
        handler.sendEmptyMessage(0x1234);
        if (MergeActivity.runAuto) {
            threadSleep(1000);
        }
        else {
            while (!MergeActivity.nextStep) {
                threadSleep(100);
            }
            MergeActivity.nextStep = false;
        }
        this.mergeStart = 1;
        animate = true;
    }
    public void show(int[] a,Handler handler){//显示完成
        finished = true;
        animate = false;
        int[] temp = new int[a.length];
//        Log.d(">>>>", "onClick:a:"+Arrays.toString(a));
        for (int i = 0; i <a.length ; i++) {
            temp[i] = a[i];
        }
        this.a = temp;
//        System.arraycopy(a, 0, this.a, 0, a.length);//已排序数组，不可直接使用指针赋值

//        Log.d(">>>>", "onClick:this a:"+Arrays.toString(this.a));
        handler.sendEmptyMessage(0x1234);

        if (MergeActivity.runAuto) {
            threadSleep(1000);
        }
        else {
            while (!MergeActivity.nextStep) {
                threadSleep(100);
            }
            MergeActivity.nextStep = false;
        }
        finished = false;
    }
    public void show(int[]a,int lo,int hi,int k, Handler handler){
        this.lo = lo;
        this.hi = hi;
        for (int j = lo; j <=hi; j++) {
            aux[k] = a[k];
        }
        this.k = k;
        y=0;
        x=0;
        handler.sendEmptyMessage(0x1234);
        threadSleep(300);//等待视图更新，必要
        xLength = ((getWidth() - a.length * 100) / 2 + iTemp * 100)-((getWidth() - a.length * 100) / 2 + k * 100);

        while(y<500) {
            y+=10;
            if (xLength == 0) x = 0;
            else if (xLength > 0) x -= ((float)Math.abs(xLength))/50f;
            else x += ((float)Math.abs(xLength))/50f;
            handler.sendEmptyMessage(0x1234);
            threadSleep(15);
        }

        if (MergeActivity.runAuto) {
            threadSleep(1000);
        }
        else {
            while (!MergeActivity.nextStep) {
                threadSleep(100);
            }
            MergeActivity.nextStep = false;
        }

    }
    public MergeView(Context context) {
        super(context);
    }

    public MergeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int N = a.length;
        super.onDraw(canvas);
        paint.setColor(0xff33ccff);
        for (int i = 0; i < N; i++) {
            if (finished){//每次merge完后显示
                paint.setColor(0xff33ccff);//blue
            }
            else {
                if (i >= lo && i <= hi) {
                    if (mergeStart==0){//被merge的显示绿色
                        paint.setColor(0xff00cc00);//green

                    }
                    else {
                        if (a[i] == aux[k]) {
                            aTemp[i] = -1;
                            iTemp = i;
                        }
                        if (aTemp[i] == -1) paint.setColor(0xffffff);//white
                        else paint.setColor(0xff00cc00);//green
                    }
                } else paint.setColor(0xff33ccff);//blue
            }


            canvas.drawRect((getWidth()-N*100)/2+i*100, 500-a[i]*50, (getWidth()-N*100)/2+80+100*i, 500, paint);
            if (aTemp[i]!=-1) {
                paint.setColor(Color.WHITE);//绘数组值
                paint.setTextSize(40);
                paint.setFakeBoldText(true);
                canvas.drawText(a[i] + "", (getWidth() - N * 100) / 2 + i * 100 + 25, 490, paint);
            }


        }
        if (animate) {
            for (int i = lo; i <=hi; i++) {
                if (!finished && mergeStart != 0) {
                    paint.setColor(0xff33ccff);//blue

                    if (i<k) {
                        paint.setColor(0xff33ccff);
                        canvas.drawRect((getWidth() - N * 100) / 2 + i * 100, 1000 - aux[i] * 50, (getWidth() - N * 100) / 2 + 80 + 100 * i, 1000, paint);
                        paint.setColor(Color.WHITE);//绘数组值
                        paint.setTextSize(40);
                        paint.setFakeBoldText(true);
                        canvas.drawText(aux[i] + "", (getWidth() - N * 100) / 2 + i * 100 + 25, 490+500, paint);
                    }
                    if (a[i]==aux[k]) {

                        paint.setColor(0xff33ccff);
//                        canvas.translate(0, y);//之后的都将偏移
                        canvas.drawRect((getWidth() - N * 100) / 2 + i * 100+x, 500 - a[i] * 50+y, (getWidth() - N * 100) / 2 + 80 + 100 * i+x, 500+y, paint);

                        paint.setColor(Color.WHITE);//绘数组值
                        paint.setTextSize(40);
                        paint.setFakeBoldText(true);
                        canvas.drawText(a[i] + "", (getWidth() - N * 100) / 2 + i * 100 + 25+x, 490+y, paint);
                    }

                }

            }
        }





    }

}
