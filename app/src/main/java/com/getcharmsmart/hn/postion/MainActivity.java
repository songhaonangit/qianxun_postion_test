package com.getcharmsmart.hn.postion;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bcgtgjyb.huanwen.customview.mylibrary.view58.Loding58View;
import com.getcharmsmart.hn.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("native-lib");
    }

    public final String FILE_NAME = Environment.getExternalStorageDirectory().getPath()+"/qxwz/gps_data.txt";

    public static final String TAG = "MainActivity";
    Button button;
    Loding58View  loding58View;
    TextView result,tv;

    StringBuilder stringBuilder;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_use_main);

        button = (Button)findViewById(R.id.bt);
        result =  (TextView)findViewById(R.id.tv_result);
        tv =(TextView)findViewById(R.id.tv);
        loding58View =(Loding58View)findViewById(R.id.add);

        tv.setVisibility(View.INVISIBLE);
        loding58View.setVisibility(View.INVISIBLE);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"---onResume()---");
        /**
         * 设置为竖屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        init();
    }

    private void init() {

        if (!NetWorkUtils.isNetworkAvailable(this)) {
            showSetNetworkUI(this);
        } else {

            if(NetWorkUtils.netCanUse(this)){
                Toast.makeText(this, "网络已连接可用...", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "当前网络不可用，请尝试更换网络连接方式，或者换个地方尝试使用...", Toast.LENGTH_SHORT).show();
            }


        }

        verifyStoragePermissions(this);


        CmdGps.openGps();
        open();
    }

     /*
     * 打开设置网络界面
     */
    public void showSetNetworkUI(final Context context) {
    // 提示对话框
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("网络设置提示")
            .setMessage("网络连接不可用,是否进行设置?")
    .setPositiveButton("设置", new DialogInterface.OnClickListener() {

    @Override
    public void onClick(DialogInterface dialog, int which) {
    // TODO Auto-generated method stub
    Intent intent = null;
    // 判断手机系统的版本 即API大于10 就是3.0或以上版本
    if (android.os.Build.VERSION.SDK_INT > 10) {

        intent = new Intent(Settings.ACTION_SETTINGS);

    }else{

        intent = new Intent();
        ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(component);
        intent.setAction("android.intent.action.VIEW");
    }
     context.startActivity(intent);

    }
    }).create();

    builder.setCancelable(false).show();


    }


    @Override
    protected void onStop() {
        super.onStop();
        close();
        CmdGps.closeGps();

        Log.d(TAG,"---onStop()---");
    }




    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有读的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CountDownTimer timer_unlock = new CountDownTimer(5000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {

           if(!CmdGps.checkGpsProcess()){
               CmdGps.openGps();
               addData();
           }
            Log.d(TAG, "---timer_unlock--onTick--CmdGps.checkGpsProcess()---"+CmdGps.checkGpsProcess());
            Log.d(TAG, "---timer_unlock--onTick--millisUntilFinished---"+millisUntilFinished+"ms");
        }

        @Override
        public void onFinish() {


            File file = new File(FILE_NAME);
            if (!file.exists() || !file.isFile()) {

                result.setText("上传位置失败\n"+"NEMA 数据不存在");
                Toast.makeText(getApplicationContext(),"上传位置失败",Toast.LENGTH_SHORT).show();

            }else {
                stringBuilder = FileUtils.readFile(FILE_NAME,"UTF_8");

                result.setText("上传位置成功\n"+stringBuilder.toString());
                Toast.makeText(getApplicationContext(),"上传位置成功",Toast.LENGTH_SHORT).show();
            }


            tv.setVisibility(View.INVISIBLE);
            loding58View.setVisibility(View.INVISIBLE);
            button.setEnabled(true);
        }
    };

    public void addData(){

        ioctl(2,1);

        tv.setVisibility(View.VISIBLE);
        loding58View.setVisibility(View.VISIBLE);
        result.setText("");
        button.setEnabled(false);
        if(timer_unlock!=null){
            timer_unlock.cancel();
        }
        timer_unlock.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 444) {

            addData();
            return true;
        }
        return false;
    }



    public native int open();

    public native int close();

    public native int ioctl(int cmd, int flag);



}
