package com.example.englishbala;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;

import java.util.List;

import static com.example.englishbala.ActivityCollector.finishAll;

public class MyActivity extends AppCompatActivity {
    private ImageView imgHead;
    private TextView name;
    private MyPopWindow welWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        imgHead=(ImageView) findViewById(R.id.imgHead);
        name=(TextView) findViewById(R.id.text_me_name);
        // 设置头像
        List<User> userList = LitePal.where("userId = ?", ConfigData.getSinaNumLogged() + "").find(User.class);
        Glide.with(MyApplication.getContext()).load(userList.get(0).getUserProfile()).into(imgHead);

        name.setText(userList.get(0).getUserName());
        ActivityCollector.addActivity(this);
    }

    public void out(View btn){

        AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
        builder.setTitle("提示")
                .setMessage("确定要退出吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
