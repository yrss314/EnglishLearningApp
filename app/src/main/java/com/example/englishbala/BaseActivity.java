package com.example.englishbala;


import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private PermissionListener mListener;

    private static final int PERMISSION_REQUESTCODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    // 权限
    public void requestRunPermission(String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(permission);
            }
        }
        if (!permissionLists.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUESTCODE);
        } else {
            //表示全都授权了
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTCODE:
                if (grantResults.length > 0) {
                    // 存放没授权的权限
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        // 说明都授权了
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }



    // 开启数据监测
    public static void prepareDailyData() {
        long currentDate = TimeController.getCurrentDateStamp();
        List<DailyData> dailyDataList = LitePal.where("dayTime = ?", currentDate + "").find(DailyData.class);

        if (dailyDataList.isEmpty()) {
            analyseJsonAndSave();
        } else {
            if (dailyDataList.get(0).getPicVertical() == null ||
                    dailyDataList.get(0).getPicHorizontal() == null)  {
                analyseJsonAndSave();
            }
        }
    }

    public static void analyseJsonAndSave() {
        byte[] imgVertical;
        byte[] imgHorizontal;

        String result = "", json, tem;
        LitePal.deleteAll(DailyData.class);
        DailyData dailyData = new DailyData();
        try {
            json = HttpHelper.requestResult(ConstantData.IMG_API);
            Log.d(TAG, "数据" + json);
            Gson gson = new Gson();
            JsonBing jsonBing = gson.fromJson(json, JsonBing.class);
            Log.d(TAG, "prepareDailyData: " + jsonBing.toString());
            tem = ConstantData.IMG_API_BEFORE + jsonBing.getImages().get(0).getUrl();
            Log.d(TAG, "URL" + tem);
            imgHorizontal = HttpHelper.requestBytes(tem);
            if (tem.indexOf("1920x1080") != -1) {
                result = tem.replace("1920x1080", "1080x1920");
            } else {
                result = tem;
            }
            imgVertical = HttpHelper.requestBytes(result);
            Gson gson2 = new Gson();

            dailyData.setPicHorizontal(imgHorizontal);
            dailyData.setPicVertical(imgVertical);
            dailyData.setDayTime(TimeController.getCurrentDateStamp() + "");
            dailyData.save();
        } catch (Exception e) {
            Log.d(TAG, "prepareDailyData: " + e.toString());
        }
    }






}
