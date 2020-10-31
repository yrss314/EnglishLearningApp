package com.example.englishbala;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseWordDBActivity extends BaseActivity {
    //private RecyclerView recyclerView;

    //private ImageView imgRecover;

    // 书单数据
    private List<ItemWordBook> itemWordBookList = new ArrayList<>();

    //private SimpleAdapter adapter;
    private BaseAdapter adapter;

    private ListView listView;

    private List<Map<String,Object>> lists;


    ImageView imgBook;

    private int[] imageViews = {R.drawable.book1,R.drawable.book2,R.drawable.book3,R.drawable.book4,R.drawable.book5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_word_db);

        listView=(ListView) findViewById(R.id.recycler_word_book_list);
        imgBook=findViewById(R.id.item_img_book);

        //init();

        // linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        initData();


//        System.out.println(itemWordBookList.get(0));
//        for ( int  i =  0 ;i < itemWordBookList.size();i++){
//            System.out.println(itemWordBookList.get(i).getBookName());
//        }尝试输出list中的书名，说明initdata成功

//        System.out.println(itemWordBookList.size());  =5

        //        准备数据源
        lists = new ArrayList<>();
        for(int i = 0;i <itemWordBookList.size();i++){
            Map<String,Object> map =new HashMap<>();
            map.put("bookname",itemWordBookList.get(i).getBookName());
            map.put("booksource",itemWordBookList.get(i).getBookSource());
            map.put("wordnum",itemWordBookList.get(i).getBookWordNum());
            map.put("bookimg", imageViews[i]);

            lists.add(map);
            //Glide.with(MyApplication.getContext()).load(itemWordBookList.get(i).getBookImg()).into(imgBook);
        }

        //Glide.with(MyApplication.getContext()).load(itemWordBookList.get(0).getBookImg()).into(imgBook);

        adapter = new SimpleAdapter(ChooseWordDBActivity.this,lists,R.layout.item_book_list
                ,new String[]{"bookname","booksource","wordnum","bookimg"}
                ,new int[]{R.id.item_text_book_name,R.id.item_text_book_source,R.id.item_text_book_word_num,R.id.item_img_book});
        listView.setAdapter(adapter);










    }

    // 初始化控件
    //private void init() {
        //recyclerView = findViewById(R.id.recycler_word_book_list);
        //imgRecover = findViewById(R.id.img_wb_recover);
    //}

    // 初始化数据
    private void initData() {
        itemWordBookList.add(new ItemWordBook(ConstantData.CET4_WORDBOOK, ConstantData.bookNameById(ConstantData.CET4_WORDBOOK), ConstantData.wordTotalNumberById(ConstantData.CET4_WORDBOOK), "来源：有道考神", ConstantData.bookPicById(ConstantData.CET4_WORDBOOK)));
        itemWordBookList.add(new ItemWordBook(ConstantData.CET6_WORDBOOK, ConstantData.bookNameById(ConstantData.CET6_WORDBOOK), ConstantData.wordTotalNumberById(ConstantData.CET6_WORDBOOK), "来源：有道考神", ConstantData.bookPicById(ConstantData.CET6_WORDBOOK)));
        itemWordBookList.add(new ItemWordBook(ConstantData.CET6ALL, ConstantData.bookNameById(ConstantData.CET6ALL), ConstantData.wordTotalNumberById(ConstantData.CET6ALL), "来源：有道考神", ConstantData.bookPicById(ConstantData.CET6ALL)));
        itemWordBookList.add(new ItemWordBook(ConstantData.KAOYAN_WORDBOOK, ConstantData.bookNameById(ConstantData.KAOYAN_WORDBOOK), ConstantData.wordTotalNumberById(ConstantData.KAOYAN_WORDBOOK), "来源：有道考神", ConstantData.bookPicById(ConstantData.KAOYAN_WORDBOOK)));
        itemWordBookList.add(new ItemWordBook(ConstantData.KAOYANALL, ConstantData.bookNameById(ConstantData.KAOYANALL), ConstantData.wordTotalNumberById(ConstantData.KAOYANALL), "来源：有道考神", ConstantData.bookPicById(ConstantData.KAOYANALL)));
    }

    @Override
    public void onBackPressed() {
        // 已登录
        if (LitePal.where("userId = ?", ConfigData.getSinaNumLogged() + "").find(UserConfig.class).get(0).getCurrentBookId() != -1)
            super.onBackPressed();
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChooseWordDBActivity.this);
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
}