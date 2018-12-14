package cn.wangsr.algorithms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Arrays;

public class MergeActivity extends AppCompatActivity {

    private LinearLayout ll_help;
    private Button btn_run;
    private int[] aux;
    private EditText edArr;
    private LinearLayout ll_setting;
    private ScrollView sl_code;
    private MergeView myView2;

    public static boolean runAuto = true;
    public  boolean runEnd = true;
    public static boolean nextStep = true;
    private CharSequence checkedStr = "自动";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {//toolbar显示返回键
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ll_help = findViewById(R.id.ll_help);
        btn_run = findViewById(R.id.btn_run);
        edArr = findViewById(R.id.edArr);
        ll_setting = findViewById(R.id.ll_setting);
        sl_code = findViewById(R.id.sl_code);
        myView2 = findViewById(R.id.mv_2);
        final CheckBox checkBox = findViewById(R.id.checkBox);
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
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) sl_code.getLayoutParams();
                    lp.addRule(RelativeLayout.BELOW,R.id.mv_2);
                    sl_code.setLayoutParams(lp);
                    ll_setting.setVisibility(View.GONE);
                    sl_code.setVisibility(View.VISIBLE);
                    checkBox.setChecked(false);

                }
            }
        });

        myView2.setOnTouchListener(new View.OnTouchListener() {//点击隐藏键盘
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



        ll_help.setOnClickListener(new View.OnClickListener() {//点击屏幕隐藏help
            @Override
            public void onClick(View v) {//点击空白隐藏help
                if (ll_help.getVisibility()==View.VISIBLE)
                {
                    ll_help.setVisibility(View.GONE);
                }
            }
        });

        RadioGroup rg = findViewById(R.id.radiogroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {//模式选择作出相应改变
                RadioButton r = findViewById(checkedId);
                checkedStr = r.getText();//获取radiobutton文字
                if (checkedStr.equals("手动")) {
                    btn_run.setEnabled(true);
                    runAuto = false;
                    nextStep = false;
                    Log.d("》》》》》》》", "onCheckedChanged: 手动");
                } else {
                    runAuto = true;
                    nextStep = true;
                    if (!runEnd) btn_run.setEnabled(false);
                    Log.d("》》》》》》》", "onCheckedChanged: 自动");
                }
            }
        });

        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(">>>>>", "onClick: runEnd"+runEnd);
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
                        Toast.makeText(MergeActivity.this, "输入格式错误", Toast.LENGTH_SHORT).show();
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
        });
    }
    private void sort(int[] a){
        aux = new int[a.length];
        myView2.show(a,handler);
        sort(a,0,a.length-1);
        Log.d(">>>>>>>", "sort: a"+ Arrays.toString(a));
    }

    private void sort(int[] a,int lo,int hi){
        if (hi<=lo) return;
        int mid = lo +(hi-lo)/2;
        sort(a,lo,mid);
        sort(a,mid+1,hi);
        merge(a,lo,mid,hi);

    }
    private  void merge(int[] a,int lo,int mid,int hi){
        int i = lo,j=mid+1;
        for (int k = lo; k <=hi; k++) {
            aux[k] = a[k];
        }

        myView2.show(a,lo,hi,handler);//绿色显示当前merge元素

        for (int k = lo;k<=hi;k++){
            if (i>mid) a[k] = aux[j++];
            else if (j>hi) a[k] = aux[i++];
            else if (aux[i]>aux[j]) a[k] = aux[j++];
            else a[k] = aux[i++];

            myView2.show(a,lo,hi,k,handler);

        }
//        System.arraycopy(a, lo, myView2.a, lo, hi + 1 - lo);
        System.arraycopy(a, lo, myView2.aTemp, lo, hi + 1 - lo);//利用辅助数组atemp标记被merge的index
        myView2.show(a,handler);//显示完成

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
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 123) myView2.invalidate();
            if (msg.what == 2) btn_run.setEnabled(false);
            else if (msg.what == 3) {
                btn_run.setEnabled(true);
//                Toast.makeText(MergeActivity.this, "排序完成", Toast.LENGTH_SHORT).show();
            }
            if (msg.what==0x1234) myView2.invalidate();
        }
    };

    /**
     * 加载action bar菜单项
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {//加载actionbar自定义菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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
                if (sl_code.getVisibility()==View.GONE) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) sl_code.getLayoutParams();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        lp.removeRule(RelativeLayout.BELOW);
                    }
                    sl_code.setLayoutParams(lp);
                    sl_code.setVisibility(View.VISIBLE);
                    ll_setting.setVisibility(View.GONE);
                }
                else {
                    sl_code.setVisibility(View.GONE);

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
