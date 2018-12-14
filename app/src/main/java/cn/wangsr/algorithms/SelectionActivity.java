package cn.wangsr.algorithms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * 选择排序activity
 */
public class SelectionActivity extends AppCompatActivity {
    private Button btn1;
    private MyView myView;
    private TextView forI;
    private TextView forJ;
    private TextView minI;
    private TextView ifMin;
    private TextView swap;
    private EditText edArr;
    private TextView tv_help;
    private LinearLayout ll_help;
    public static boolean runAuto = true;
    private boolean runEnd = true;
    public static boolean nextStep = true;
    private CharSequence checkedStr = "自动";
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==123) myView.invalidate();
            if (msg.what==2) btn1.setEnabled(false);
            else if(msg.what==3) {
                btn1.setEnabled(true);
                Toast.makeText(SelectionActivity.this,"排序完成",Toast.LENGTH_SHORT).show();
            }
            if (msg.what==0x00a) {
                forI.setBackgroundColor(0xffff6600);
                minI.setBackgroundColor(Color.TRANSPARENT);
                forJ.setBackgroundColor(Color.TRANSPARENT);
                ifMin.setBackgroundColor(Color.TRANSPARENT);
                swap.setBackgroundColor(Color.TRANSPARENT);

            }
            else if (msg.what==0x00b) {
                forI.setBackgroundColor(Color.TRANSPARENT);
                minI.setBackgroundColor(0xffff6600);
                forJ.setBackgroundColor(Color.TRANSPARENT);
                ifMin.setBackgroundColor(Color.TRANSPARENT);
                swap.setBackgroundColor(Color.TRANSPARENT);
            }
            else if (msg.what==0x00c) {
                forI.setBackgroundColor(Color.TRANSPARENT);
                minI.setBackgroundColor(Color.TRANSPARENT);
                forJ.setBackgroundColor(0xffff6600);
                ifMin.setBackgroundColor(Color.TRANSPARENT);
                swap.setBackgroundColor(Color.TRANSPARENT);
            }
            else if (msg.what==0x00d) {
                forI.setBackgroundColor(Color.TRANSPARENT);
                minI.setBackgroundColor(Color.TRANSPARENT);
                forJ.setBackgroundColor(Color.TRANSPARENT);
                ifMin.setBackgroundColor(0xffff6600);
                swap.setBackgroundColor(Color.TRANSPARENT);
            }
            if (msg.what==0x00e) {
                forI.setBackgroundColor(Color.TRANSPARENT);
                minI.setBackgroundColor(Color.TRANSPARENT);
                forJ.setBackgroundColor(Color.TRANSPARENT);
                ifMin.setBackgroundColor(Color.TRANSPARENT);
                swap.setBackgroundColor(0xffff6600);
            }


        }
    };
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {//toolbar显示返回键
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowTitleEnabled(false);
        }
        myView = findViewById(R.id.mv_1);
        btn1 = findViewById(R.id.btn_1);
        forI = findViewById(R.id.tv_l1);
        minI = findViewById(R.id.tv_l2);
        forJ = findViewById(R.id.tv_l3);
        ifMin = findViewById(R.id.tv_l4);
        swap = findViewById(R.id.tv_l5);
        edArr = findViewById(R.id.edArr);
        tv_help = findViewById(R.id.tv_help);
        ll_help = findViewById(R.id.ll_help);
        ll_help.setOnClickListener(new View.OnClickListener() {//点击屏幕隐藏help
            @Override
            public void onClick(View v) {//点击空白收起help
                if (tv_help.getVisibility()==View.VISIBLE)
                {
                    ll_help.setVisibility(View.GONE);
                }
            }
        });
        final LinearLayout ll_code = findViewById(R.id.ll_code);
        ll_code.setOnTouchListener(new View.OnTouchListener() {//点击隐藏键盘
            @Override
            public boolean onTouch(View v, MotionEvent event) {//touch代码块时关闭输入法
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
                edArr.clearFocus();
                return false;
            }
        });
        edArr.setOnTouchListener(new View.OnTouchListener() {//排序时不可输入
            @Override
            public boolean onTouch(View v, MotionEvent event) {//排序时不可输入
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
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton r = findViewById(checkedId);
                checkedStr = r.getText();//获取radiobutton文字
                if (checkedStr.equals("手动")){
                    btn1.setEnabled(true);
                    runAuto = false;
                    nextStep = false;
                }
                else{
                    runAuto = true;
                    nextStep = true;
                    if (!runEnd) btn1.setEnabled(false);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//加载actionbar自定义菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_simple, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * actionbar 事件响应
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
            case R.id.help://help按钮
                Log.d(">>>>>>>>>>>>", "onOptionsItemSelected: ");
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

    /**
     * get Array from EditText
     * @return Array need to be sorted
     */

    private int[] getArray(){
        String arrStr = String.valueOf(edArr.getText());
        if (!arrStr.equals("")){
            String[] arrStrs = arrStr.split("\\s+");
            int[] a = new int[arrStrs.length];
            for(int i=0;i<arrStrs.length;i++){
                a[i] = Integer.parseInt(arrStrs[i]);
            }
            return a;
        }
        else return getResources().getIntArray(R.array.initArray);

    }

    /**
     * the run button actionlistener
     * @param view
     */
    public void runSelection(View view) {
        if (edArr.isFocused()) edArr.clearFocus();
        edArr.clearFocus();
        if (checkedStr.equals("自动")) {
            runAuto = true;
            nextStep = true;

        }
        else runAuto = false;
        if (runEnd){//若排序未结束，则不开启新线程
            int[] a = null;
            try {
                a = getArray();
            }
            catch (Exception e){
                Toast.makeText(SelectionActivity.this,"输入格式错误",Toast.LENGTH_SHORT).show();
                return;
            }
            final int[] finalA = a;
            new Thread() {
                public void run() {
                    runEnd = false;
                    if(runAuto) handler.sendEmptyMessage(2);//自动运行时禁用按钮
                    sort(finalA);
                    runEnd = true;
                    handler.sendEmptyMessage(3);//开启按钮,toast提示完成

                }
            }.start();
        }
        if (!runAuto && !runEnd){
            nextStep = true;//手动模式时，单击下一步标注位
        }
    }

    /**
     * 选择排序算法
     * @param a 需排序数组
     */

    private void sort(int[] a){
        int N = a.length;
        myView.a = a;
        MyView.show(myView,-1,-1,-1,handler);
        for (int i = 0; i < N; i++) {
            handler.sendEmptyMessage(0x00a);
            int min = i;
            handler.sendEmptyMessage(0x00b);
            myView.min = min;
            MyView.show(myView,min,-1,-1,handler);
            for (int j = i+1; j < N; j++) {
                handler.sendEmptyMessage(0x00c);
                MyView.show(myView,min,j,-1,handler);
                if (a[j]<a[min]) {
                    min = j;
                    handler.sendEmptyMessage(0x00d);
                    MyView.show(myView, min, -1, -1,handler);
                }
            }
            int temp = a[i];
            a[i] = a[min];
            a[min] = temp;
            handler.sendEmptyMessage(0x00e);
            MyView.show(myView,min,-1,i,handler);

        }
    }

}
