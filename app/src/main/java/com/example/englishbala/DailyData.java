package com.example.englishbala;

import org.litepal.crud.LitePalSupport;

public class DailyData extends LitePalSupport {

    private int id;

    private byte[] picVertical;

    private byte[] picHorizontal;



    // 更新时间
    private String dayTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPicVertical() {
        return picVertical;
    }

    public void setPicVertical(byte[] picVertical) {
        this.picVertical = picVertical;
    }

    public byte[] getPicHorizontal() {
        return picHorizontal;
    }

    public void setPicHorizontal(byte[] picHorizontal) {
        this.picHorizontal = picHorizontal;
    }



    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }


}
