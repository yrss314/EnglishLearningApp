package com.example.englishbala;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityOptionsCompat;

import java.util.ArrayList;
import java.util.List;
//程序如果启动太多的Activity，叠在一起，想要立刻退出程序，需要连续点击多次的Back键，太麻烦了，按Home键只会
//将程序挂起，并没有退出程序，这时候我们该怎么办呢？
//
//解决思路：
//
//只需要用一个专门的集合类对所有的活动进行管理就可以了，新建一个ActivityCollector类作为活动管理器
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        //MainActivity.needRefresh = true;   //why need??
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    // 启动新的Activity
    /*
     * 注意：
     * Context中有一个startActivity方法，Activity继承自Context，重载了startActivity方法
     * 如果使用Activity的startActivity方法，不会有任何限制
     * 而如果使用Context的startActivity方法的話，就需要开启一个新的的task
     * 遇到这个异常，是因为使用了Context的startActivity方法。解决办法是，加一个flag
     *
     * */
    public static void startOtherActivity(Context context, Class cls) {
        Intent intent = new Intent();
        intent.setClass(MyApplication.getContext(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startOtherActivity(Context context, Class cls, ActivityOptionsCompat activityOptionsCompat) {
        Intent intent = new Intent();
        intent.setClass(MyApplication.getContext(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent, activityOptionsCompat.toBundle());
    }

}
//在活动管理器中，我们通过一个List来暂存活动，然后提供了一个addActivity()方法向 List中添加一个活动，
//提供了一个 removeActivity()方法从 List中移除活动，最后提供了一个 finishAll()方法将List中存储的
//活动全部都销毁掉。
//不管你想在什么地方退出程序，只需要调用 ActivityCollector.finishAll()方法就可以全部移除了~~