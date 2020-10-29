package com.example.englishbala;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    private PermissionListener mListener;

    private static final int PERMISSION_REQUESTCODE = 100;

    public interface PermissionListener {

        //已授权
        void onGranted();

        //未授权
        void onDenied(List<String> deniedPermission);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        Log.d(TAG, getClass().getSimpleName());

        if (ConfigData.getIsNight()) {
            // 沉浸式状态栏，设置深色
            ImmersionBar.with(this)
                    .statusBarDarkFont(false)
                    .init();
        } else {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)
                    .init();
        }

        ActivityCollector.addActivity(this);

        // 防止输入法将布局顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }
}