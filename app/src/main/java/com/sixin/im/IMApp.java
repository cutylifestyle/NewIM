package com.sixin.im;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.sixin.im.util.Utils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class IMApp extends Application {
    // TODO: 2018/5/28 这个邀请设置是不是存在问题
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        //用于管理activity的工具类
        Utils.init(this);

        //初始化EaseUI
        EMOptions options = new EMOptions();
        //设置需要同意后才能接受邀请
        options.setAcceptInvitationAlways(false);
        //设置需要同意后才能接受群邀请
        options.setAutoAcceptGroupInvitation(false);
        EaseUI.getInstance().init(this, options);

        //初始化内存泄漏检测工具
        refWatcher = setupLeakCanary();
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        IMApp leakApplication = (IMApp) context.getApplicationContext();
        return leakApplication.refWatcher;
    }
}
