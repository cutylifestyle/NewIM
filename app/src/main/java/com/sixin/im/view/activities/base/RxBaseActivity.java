package com.sixin.im.view.activities.base;

import android.os.Bundle;

import com.sixin.im.IMApp;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class RxBaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        IMApp.getRefWatcher(getApplicationContext()).watch(this);
    }
}
