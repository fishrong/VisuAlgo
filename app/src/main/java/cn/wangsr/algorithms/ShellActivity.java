package cn.wangsr.algorithms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
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

import java.util.Arrays;

public class ShellActivity extends AppCompatActivity {
    private Button btn1;
    private MyView myView;
    private TextView tv_l4;
    private TextView tv_l5;
    private TextView tv_l6;
    private TextView tv_l7;
    private TextView tv_l9;
    private TextView tv_l10;
    private EditText edArr;
    private TextView tv_help;
    private LinearLayout ll_help;
    private LinearLayout ll_code;
    private ScrollView sl_code;
    public static boolean runAuto = true;
    private boolean runEnd = true;
    public static boolean nextStep = true;
    private CharSequence checkedStr = "自动";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        init();


    }

    /**
     * 加载action bar菜单项
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {//加载actionbar自定义菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_simple, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {//toolbar显示返回键
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        myView = findViewById(R.id.mv_1);
        btn1 = findViewById(R.id.btn_1);
        tv_l4 = findViewById(R.id.tv_l4);
        tv_l5 = findViewById(R.id.tv_l5);
        tv_l6 = findViewById(R.id.tv_l6);
        tv_l7 = findViewById(R.id.tv_l7);
        tv_l9 = findViewById(R.id.tv_l9);
        tv_l10 = findViewById(R.id.tv_l10);
        edArr = findViewById(R.id.edArr);
        sl_code = findViewById(R.id.sl_code);
        tv_help = findViewById(R.id.tv_help);
        ll_help = findViewById(R.id.ll_help);
        ll_code = findViewById(R.id.ll_code);

        myView.minColor = 0xff00cc00;
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
        RadioGroup rg = findViewById(R.id.radiogroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//模式选择作出相应改变
                RadioButton r = findViewById(checkedId);
                checkedStr = r.getText();//获取radiobutton文字
                if (checkedStr.equals("手动")) {
                    btn1.setEnabled(true);
                    runAuto = false;
                    nextStep = false;
                } else {
                    runAuto = true;
                    nextStep = true;
                    if (!runEnd) btn1.setEnabled(false);
                }
            }
        });

    }

    private void sort(int[] a){
        int N = a.length;
        myView.a = a;
        int h = 1;
        while(h < N/3) h = 3*h + 1;
        while (h>=1) {
            handler.sendEmptyMessage(0x00a);
            myView.h = h;
            MyView.show(myView, -1, -1, -1, handler);
            for (int i = h; i < N; i++) {
                handler.sendEmptyMessage(0x00b);
                for (int j = i; j < N & j >= h; j-=h) {
                    handler.sendEmptyMessage(0x00c);
                    MyView.show(myView, j - h, j, -1, handler);
                    if (a[j] > a[j - h]) break;
                    int temp = a[j];
                    a[j] = a[j - h];
                    a[j - h] = temp;
                    handler.sendEmptyMessage(0x00d);
                    MyView.show(myView, j - h, j, -1, handler);
                }
                MyView.show(myView, i, -1, i, handler);
            }
            h = h/3;
            handler.sendEmptyMessage(0x00e);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.sendEmptyMessage(0x00f);
    }
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

    public void runShell(View view) {
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
                Toast.makeText(ShellActivity.this, "输入格式错误", Toast.LENGTH_SHORT).show();
                return;
            }
            final int[] finalA = a;
            new Thread() {
                public void run() {
                    runEnd = false;
                    if (runAuto) handler.sendEmptyMessage(2);//自动运行时禁用按钮
                    sort(finalA);
                    runEnd = true;
                    handler.sendEmptyMessage(3);//开启按钮,toast提示完成

                }
            }.start();
        }
        if (!runAuto && !runEnd) {
            nextStep = true;//手动模式时，单击下一步标注位
        }
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) myView.invalidate();
            if (msg.what == 2) btn1.setEnabled(false);
            else if (msg.what == 3) {
                btn1.setEnabled(true);
                Toast.makeText(ShellActivity.this, "排序完成", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 0x00a) {
                tv_l4.setBackgroundColor(0xffff6600);
                tv_l5.setBackgroundColor(Color.TRANSPARENT);
                tv_l6.setBackgroundColor(Color.TRANSPARENT);
                tv_l7.setBackgroundColor(Color.TRANSPARENT);
                tv_l9.setBackgroundColor(Color.TRANSPARENT);
                tv_l10.setBackgroundColor(Color.TRANSPARENT);

            } else if (msg.what == 0x00b) {
                tv_l4.setBackgroundColor(Color.TRANSPARENT);
                tv_l5.setBackgroundColor(0xffff6600);
                tv_l6.setBackgroundColor(Color.TRANSPARENT);
                tv_l7.setBackgroundColor(Color.TRANSPARENT);
                tv_l9.setBackgroundColor(Color.TRANSPARENT);
                tv_l10.setBackgroundColor(Color.TRANSPARENT);
            } else if (msg.what == 0x00c) {
                tv_l4.setBackgroundColor(Color.TRANSPARENT);
                tv_l5.setBackgroundColor(Color.TRANSPARENT);
                tv_l6.setBackgroundColor(0xffff6600);
                tv_l7.setBackgroundColor(Color.TRANSPARENT);
                tv_l9.setBackgroundColor(Color.TRANSPARENT);
                tv_l10.setBackgroundColor(Color.TRANSPARENT);

            }
            else if (msg.what == 0x00d) {
                tv_l4.setBackgroundColor(Color.TRANSPARENT);
                tv_l5.setBackgroundColor(Color.TRANSPARENT);
                tv_l6.setBackgroundColor(Color.TRANSPARENT);
                tv_l7.setBackgroundColor(0xffff6600);
                tv_l9.setBackgroundColor(Color.TRANSPARENT);
                sl_code.fullScroll(ScrollView.FOCUS_DOWN);
                tv_l10.setBackgroundColor(Color.TRANSPARENT);

            }
            else if (msg.what == 0x00e) {
                tv_l4.setBackgroundColor(Color.TRANSPARENT);
                tv_l5.setBackgroundColor(Color.TRANSPARENT);
                tv_l6.setBackgroundColor(Color.TRANSPARENT);
                tv_l7.setBackgroundColor(Color.TRANSPARENT);
                tv_l9.setBackgroundColor(0xffff6600);
                tv_l10.setBackgroundColor(Color.TRANSPARENT);

            }
            else if (msg.what == 0x00f) {
                tv_l4.setBackgroundColor(Color.TRANSPARENT);
                tv_l5.setBackgroundColor(Color.TRANSPARENT);
                tv_l6.setBackgroundColor(Color.TRANSPARENT);
                tv_l7.setBackgroundColor(Color.TRANSPARENT);
                tv_l9.setBackgroundColor(Color.TRANSPARENT);
                tv_l10.setBackgroundColor(0xffff6600);

            }


        }
    };

}
