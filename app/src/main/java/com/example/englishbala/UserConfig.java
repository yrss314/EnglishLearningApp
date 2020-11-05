package com.example.englishbala;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class UserConfig extends LitePalSupport {

    @Column(unique = true)
    private long id;

    @Column(defaultValue = "-1")
    // 当前正在使用的书目
    // 如果为-1，说明未创建书目，是个新用户
    private int currentBookId;



    // 归属用户
    private long userId;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrentBookId() {
        return currentBookId;
    }

    public void setCurrentBookId(int currentBookId) {
        this.currentBookId = currentBookId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }



}
