package cn.wangsr.algorithms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 快速排序
 */

public class QuickActivity extends AppCompatActivity {
    private QuickView quickView;
    private TextView tv_code;
    private ScrollView sl_code;
    private Spannable span;
    private LinearLayout ll_help;
    private LinearLayout ll_setting;




    private EditText edArr;
    private Button btn_run;
    private Button btn_run_circle;
    public static boolean runAuto = true;
    public  boolean runEnd = true;
    public static boolean nextStep = true;
    private CharSequence checkedStr = "自动";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);
        //菜单项设置
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {//toolbar显示返回键
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        quickView = findViewById(R.id.mv_quick);
        tv_code = findViewById(R.id.tv_code);
        sl_code = findViewById(R.id.sl_code);
        ll_help = findViewById(R.id.ll_help);
        ll_setting = findViewById(R.id.ll_setting);

        span = new SpannableString(tv_code.getText());

        edArr = findViewById(R.id.edArr);
        btn_run = findViewById(R.id.btn_run);
        btn_run_circle = findViewById(R.id.btn_run_circle);


        final ScrollView sl_content = findViewById(R.id.sl_content);
        //显示小button
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sl_content.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

//                    System.out.println("heightparams:"+(int)(sl_code.getHeight()/(getResources().getDisplayMetrics().density)+0.5f));
                    if (scrollY>572 || (int)(sl_code.getHeight()/(getResources().getDisplayMetrics().density)+0.5f)==180){
                        btn_run_circle.setVisibility(View.GONE);

                    }
                    else {
                        btn_run_circle.setVisibility(View.VISIBLE);
                    }
                }
            });
        }


        RadioGroup rg = findViewById(R.id.radiogroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//模式选择作出相应改变
                RadioButton r = findViewById(checkedId);
                checkedStr = r.getText();//获取radiobutton文字
                if (checkedStr.equals("手动")) {
                    btn_run.setEnabled(true);
                    btn_run_circle.setEnabled(true);
                    runAuto = false;
                    nextStep = false;
                    Log.d("》》》》》》》", "onCheckedChanged: 手动");
                } else {
                    runAuto = true;
                    nextStep = true;
                    if (!runEnd) {
                        btn_run.setEnabled(false);
                        btn_run_circle.setEnabled(false);
                    }
                    Log.d("》》》》》》》", "onCheckedChanged: 自动");
                }
            }
        });

        ll_help.setOnClickListener(new View.OnClickListener() {//点击屏幕隐藏help
            @Override
            public void onClick(View v) {//点击空白隐藏help
                if (ll_help.getVisibility()==View.VISIBLE)
                {
                    ll_help.setVisibility(View.GONE);
                }
            }
        });







        final LinearLayout ll_code = findViewById(R.id.ll_code);
        ll_code.setOnTouchListener(new View.OnTouchListener() {//点击隐藏键盘
            @Override
            public boolean onTouch(View v, MotionEvent event) {//点击code收起键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                edArr.clearFocus();
                return false;
            }
        });
        edArr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {//排序时不可输入数组
                Log.d(">>>>>>>>>", "onClick: edArr"+runEnd+edArr.isFocusable());
                if (runEnd) {
                    edArr.setFocusable(true);
                    edArr.setFocusableInTouchMode(true);
                }
                else {
                    edArr.setFocusable(false);
                    edArr.setFocusableInTouchMode(false);
                }

                return false;
            }
        });



    }

    /**
     * 高亮显示代码
     * @param what 标志
     * @param delay 是否延时
     */

    private void showCode(int what,boolean delay){
        handler.sendEmptyMessage(what);
        if (delay) {
            if (QuickActivity.runAuto) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                while (!QuickActivity.nextStep) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                QuickActivity.nextStep = false;
            }
        }
    }

    /**
     * 排序
     * @param a
     */
    private void sort(int[] a){
        showCode(0x0001,false);
        quickView.show(a,handler,0,a.length-1,"partitionFinished");

        sort(a,0,a.length-1);
        showCode(0x0002,false);
        quickView.show(a,handler,0,a.length-1,"sortFinished");

    }

    private void sort(int[] a,int lo,int hi){
        showCode(0x0003,false);
        quickView.show(a,handler,lo,hi,"partitionFinished");
        if (hi<=lo){
            showCode(0x0015,true);
            return;
        }

        showCode(0x0004,true);
        int j = partition(a,lo,hi);
        showCode(0x0005,true);
        sort(a, lo, j-1);
        showCode(0x0006,true);
        sort(a,j+1,hi);

    }

    /**
     * 切分
     * @param a
     * @param lo
     * @param hi
     * @return
     */
    private int partition(int[] a,int lo,int hi){
        showCode(0x0007,true);
        int i = lo,j = hi+1;
        showCode(0x000a,false);
        int v =a[lo];
//        quickView.show(a,handler,lo,hi,"partitionFinished");
        quickView.show(a,handler,-1,-1,lo,hi);
        showCode(0x0008,true);
        while (true){
            showCode(0x0009,false);
            while (a[++i]<v) {
//                showCode(0x000a);
                quickView.show(a,handler,i,j,lo,hi);
                if (i==hi){
                    showCode(0x000b,false);
                    break;
                }
            }
            quickView.show(a,handler,i,j,lo,hi);
            showCode(0x000c,true);
            while (a[--j]>v) {

                quickView.show(a,handler,i,j,lo,hi);
//                showCode(0x000d);
                if (j==lo) {
                    showCode(0x000e,false);
                    break;
                }
            }
            quickView.show(a,handler,i,j,lo,hi);
            showCode(0x0010,true);
            if (i>=j) {
                showCode(0x0011,true);
                break;
            }
            showCode(0x0012,true);
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            quickView.show(a,handler,i,j,lo,hi);
        }
        showCode(0x0013,true);
        int temp = a[lo];
        a[lo] = a[j];
        a[j] = temp;
        quickView.show(a,handler,i,j,lo,hi);
        showCode(0x0014,false);
        quickView.show(a,handler,lo,hi,"partitionFinished");
        return j;
    }


    /**
     * 从输入框获取数组
     * @return
     */
    private int[] getArray() {
        String arrStr = String.valueOf(edArr.getText());
        if (!arrStr.equals("")) {
            String[] arrStrs = arrStr.split("\\s+");
            int[] a = new int[arrStrs.length];
            for (int i = 0; i < arrStrs.length; i++) {
                a[i] = Integer.parseInt(arrStrs[i]);
            }
            return a;
        } else return getResources().getIntArray(R.array.initArray);

    }
    public void runQuick(View view) {
        if (edArr.isFocused()) edArr.clearFocus();//开始排序后，输入框失去焦点

        if (checkedStr.equals("自动")) {
            runAuto = true;
            nextStep = true;

        } else runAuto = false;
        if (runEnd) {//若排序未结束，则不开启新线程
            int[] a = null;
            try {
                a = getArray();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(QuickActivity.this, "输入格式错误", Toast.LENGTH_SHORT).show();
                return;
            }
            final int[] finalA = a;
            new Thread() {
                public void run() {
                    runEnd = false;
                    if (runAuto) handler.sendEmptyMessage(0x1232);//自动运行时禁用按钮

                    sort(finalA);
                    runEnd = true;
                    handler.sendEmptyMessage(0x1233);//开启按钮,toast提示完成

                }
            }.start();
        }
        if (!runAuto && !runEnd) {
            nextStep = true;//手动模式时，单击下一步标注位
        }


    }

    /**
     * 加载action bar菜单项
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {//加载actionbar自定义菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.setting);
        menuItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * action bar菜单响应
     * @param item
     * @return
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//actionbar点击响应
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();//返回
                return true;
            case R.id.help:
                if (ll_help.getVisibility()==View.GONE) {
                    ll_help.setVisibility(View.VISIBLE);
                    ll_help.setBackgroundColor(R.color.LightGrey);
                }
                else {
                    ll_help.setVisibility(View.GONE);

                }
                return true;
            case R.id.setting:
                if (ll_setting.getVisibility()==View.GONE) {
                    ll_setting.setVisibility(View.VISIBLE);
                    sl_code.setVisibility(View.GONE);
                }
                else {
                    ll_setting.setVisibility(View.GONE);

                }
                return true;
            case R.id.code:
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) sl_code.getLayoutParams();
                int height = (int)(sl_code.getHeight()/(getResources().getDisplayMetrics().density)+0.5f);
                final int height180 = (int)(180*(getResources().getDisplayMetrics().density)+0.5f);
                Log.d("height", "onOptionsItemSelected: +"+sl_code.getLayoutParams().height);
                if (height==180) {
                    btn_run_circle.setVisibility(View.VISIBLE);
                    lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                }
                else {
                    btn_run_circle.setVisibility(View.GONE);
                    lp.height = height180;


                }

                sl_code.setLayoutParams(lp);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x1232) {
                btn_run.setEnabled(false);
                btn_run_circle.setEnabled(false);
            }
            else if (msg.what == 0x1233) {
                btn_run.setEnabled(true);
                btn_run_circle.setEnabled(true);
//                Toast.makeText(QuickActivity.this, "排序完成", Toast.LENGTH_SHORT).show();
            }
            if (msg.what==0x1234) quickView.invalidate();
            else{
                span.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 528, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new StyleSpan(Typeface.NORMAL), 0, 528, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            switch (msg.what) {
                case 0x0001:
                    sl_code.setScrollY(0);

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 35, 61, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 35, 61, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0002:
                    sl_code.setScrollY(0);
                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 63, 64, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 63, 64, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0003:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 113, 132, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 113, 132, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0015:
                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 132, 139, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 132, 139, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;

                case 0x0004:
                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 146, 173, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 146, 173, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0005:

                    sl_code.setScrollY(186);
                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 180, 195, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 180, 195, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0006:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 202, 216, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 202, 216, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0007:
                    sl_code.setScrollY(515);

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 278, 305, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 278, 305, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0008:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 328, 339, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 328, 339, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0009:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 351, 366, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 351, 366, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x000a:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 307, 321, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 307, 321, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x000b:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 377, 383, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 377, 383, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x000c:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 390, 410, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 390, 410, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x000d:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 411, 420, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 411, 420, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x000e:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 421, 427, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 421, 427, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0010:

//                    sl_code.setScrollY(761);
                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 438, 447, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 438, 447, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0011:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 447, 455, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 447, 455, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0012:
                    sl_code.setScrollY(630);
                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 462, 480, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 462, 480, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0013:
                    sl_code.setScrollY(797);
                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 490, 512, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 490, 512, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
                case 0x0014:

                    span.setSpan(new ForegroundColorSpan(Color.YELLOW), 519, 528, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 519, 528, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_code.setText(span);
                    break;
            }




            }
        }
    };
}
