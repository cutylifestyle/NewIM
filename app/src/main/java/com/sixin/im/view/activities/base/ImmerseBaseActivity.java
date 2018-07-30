package com.sixin.im.view.activities.base;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.sixin.im.R;

public class ImmerseBaseActivity extends RxBaseActivity {

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.color_status_bar)
                     .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();
    }
}
