package com.example.englishbala;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    EditText et;
    EditText met;
    ImageView img;
    private static final int Gallary = 1;
    private static final int TakePhoto = 2;
    private Toolbar myToolbar;
    private String old_content = "";
    private String old_time = "";
    private int old_Tag = 1;
    private long id = 0;
    private byte old_pic[]=new byte[125*250];
    private int openMode = 0;
    private int tag = 1;
    public Intent myintent = new Intent(); // message to be sent
    private boolean tagChange = false;
    private final String TAG = "tag";
    private byte buff[] = new byte[125*250];
    public boolean isNightMode(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return sharedPreferences.getBoolean("nightMode", false);
    }
    public void setNightMode(){
        setTheme(R.style.DayTheme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);


        myToolbar = findViewById(R.id.my_Toolbar);
        img=findViewById(R.id.imageView2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置toolbar取代actionbar

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myintent.putExtra("img",buff);
                autoSetMessage();
                System.out.println("auto"+buff);
                setResult(RESULT_OK, myintent);
                finish();
            }
        });
        et = findViewById(R.id.et);
        Intent getIntent = getIntent();
        openMode = getIntent.getIntExtra("mode", 0);

        if (openMode == 3) {//打开已存在的note
            id = getIntent.getLongExtra("id", 0);
            old_content = getIntent.getStringExtra("content");
            old_time = getIntent.getStringExtra("time");
            old_Tag = getIntent.getIntExtra("tag", 1);
            et.setText(old_content);
            System.out.println(old_content);
            et.setSelection(old_content.length());

            old_pic=getIntent.getByteArrayExtra("img");
            System.out.println("打开已存在"+old_pic);

            Bitmap bitmapNew = BitmapFactory.decodeByteArray(old_pic, 0, old_pic.length);
            img.setImageBitmap(bitmapNew);




        }

        setNightMode();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete://删除图标
                new AlertDialog.Builder(EditActivity.this)
                        .setMessage("删除吗？")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (openMode == 4){ // new note
                                    myintent.putExtra("mode", -1);
                                    setResult(RESULT_OK, myintent);
                                }
                                else { // existing note
                                    myintent.putExtra("mode", 2);
                                    myintent.putExtra("id", id);
                                    setResult(RESULT_OK, myintent);
                                }
                                finish();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;
            case R.id.addImg://上传图片图标
                final CharSequence[] items = { "手机相册", "相机拍摄" };
                AlertDialog dlg = new AlertDialog.Builder(EditActivity.this).setTitle("选择图片").setItems(items,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int item) {
                                if(item==1){
                                    Intent getImageByCamera= new Intent("android.media.action.IMAGE_CAPTURE");
                                    startActivityForResult(getImageByCamera, TakePhoto);
                                }else{
                                    Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                                    getImage.addCategory(Intent.CATEGORY_OPENABLE);
                                    getImage.setType("image/*");
                                    startActivityForResult(getImage, Gallary);
                                }
                            }
                        }).create();
                dlg.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }





    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK){
            autoSetMessage();
            setResult(RESULT_OK, myintent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void autoSetMessage(){
        if(openMode == 4){//新增
            if(et.getText().toString().length() == 0){
                myintent.putExtra("mode", -1);
            }
            else{

                myintent.putExtra("mode", 0);
                myintent.putExtra("content", et.getText().toString());
                myintent.putExtra("time", dateToStr());
                myintent.putExtra("tag", tag);
                System.out.println("auto方法中"+buff);
                myintent.putExtra("img", buff);
            }
        }
        else {//修改
//            if (et.getText().toString().equals(old_content) && !tagChange)//仅当未修改文字保持不变（所以当文字未改动图片改动时，note和原来一样。所以干脆改动未改动都视为改动重新获取数据存入数据库）
//                myintent.putExtra("mode", -1); // edit nothing
//            else {
                byte[] getbuff;
                Bitmap bitmappp = ((BitmapDrawable)img.getDrawable()).getBitmap();
                if (bitmappp != null) {
                    getbuff = Bitmap2Bytes(bitmappp);


                myintent.putExtra("mode", 1); //edit the content
                myintent.putExtra("content", et.getText().toString());
                myintent.putExtra("time", dateToStr());
                myintent.putExtra("id", id);
                myintent.putExtra("tag", tag);
                System.out.println("auto方法中"+buff);
                myintent.putExtra("img", getbuff);}
                else {
                    myintent.putExtra("mode", 1); //edit the content
                    myintent.putExtra("content", et.getText().toString());
                    myintent.putExtra("time", dateToStr());
                    myintent.putExtra("id", id);
                    myintent.putExtra("tag", tag);
                }
           // }
        }
    }

    public String dateToStr(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public void share(View btn){
        String bb=et.getText().toString();
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "share");//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, bb);//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "share");
        startActivity(share_intent);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        ContentResolver resolver = getContentResolver();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Gallary:
                    Uri originalUri = intent.getData();
                    Bitmap bitmap = null;
                    try {
                        Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
                        bitmap = resizeImage(originalBitmap, 200, 200);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (bitmap != null) {
                       img.setImageBitmap(bitmap);
                       buff = Bitmap2Bytes(bitmap);
                       System.out.println(buff);
                       myintent.putExtra("img",buff);

                       System.out.println("intent"+buff);

                    } else {
                        Toast.makeText(EditActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case TakePhoto:
                    Bundle extras = intent.getExtras();
                    Bitmap originalBitmap1 = (Bitmap) extras.get("data");
                    if (originalBitmap1 != null) {
                        bitmap = resizeImage(originalBitmap1, 200, 200);
                        ImageSpan imageSpan = new ImageSpan(EditActivity.this, bitmap);

                        img.setImageBitmap(bitmap);
                        buff = Bitmap2Bytes(bitmap);
                        System.out.println("intent"+buff);
                        myintent.putExtra("img",buff);
                    } else {
                        Toast.makeText(EditActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }
    private Bitmap resizeImage(Bitmap originalBitmap, int newWidth, int newHeight){
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        //计算宽、高缩放率
        float scanleWidth = (float)newWidth/width;
        float scanleHeight = (float)newHeight/height;
        Matrix matrix = new Matrix();
        matrix.postScale(scanleWidth,scanleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(originalBitmap,0,0,width,height,matrix,true);
        return resizedBitmap;
    }
    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
