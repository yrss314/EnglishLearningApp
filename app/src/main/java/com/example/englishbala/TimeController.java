package com.example.englishbala;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeController {

    public static final String FORMAT_NOTIME = "yyyy-MM-dd";

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_NOTIME);


    private static final String TAG = "TimeController";

    /*----日期类----*/

    // 得到当前日期戳(不带时间，只有日期)
    public static long getCurrentDateStamp() {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentDate = cal.get(Calendar.DATE);
        long time = 0;
        try {
            time = simpleDateFormat.parse(currentYear + "-" + currentMonth + "-" + currentDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getCurrentDateStamp: " + time);
        return time;
    }



}

