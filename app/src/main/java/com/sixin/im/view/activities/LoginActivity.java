package com.sixin.im.view.activities;

import android.os.Bundle;

import com.sixin.im.IMApp;
import com.sixin.im.R;
import com.sixin.im.view.activities.base.ImmerseBaseActivity;

public class LoginActivity extends ImmerseBaseActivity {
    // TODO: 2018/6/10 黄油刀绑定事件
    // TODO: 2018/6/10 三方登录果冻效果的popupwindow
    // TODO: 2018/6/10 自定义progressBar 苹果风格的progressbar
    // TODO: 2018/6/11 登录页面的图标最后需要换成矢量图，在平板上面已经失真了
    // TODO: 2018/6/12 activity跳转优化
    // TODO: 2018/6/13 floatingActionBar  SnackBar的使用
    // TODO: 2018/6/13 ToolBar外层包裹了AppBarLayout
    // TODO: 2018/6/13 进入到loginActivity的时候显示出了桌面的内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawable(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IMApp.getRefWatcher(getApplicationContext()).watch(this);
    }
}
