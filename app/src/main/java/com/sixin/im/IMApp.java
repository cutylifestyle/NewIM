package com.sixin.im;

import android.app.Application;

import com.sixin.im.util.activityutil.Utils;

public class IMApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //用于管理activity的工具类
        Utils.init(this);
    }
}
