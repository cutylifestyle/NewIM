package com.sixin.im.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sixin.im.R;
import com.sixin.im.widget.parallaxpager.ParallaxContainer;

public class GuidePageActivity extends Activity {
    // TODO: 2018/5/12 判断是否已经打开过引导页
    // TODO: 2018/4/29 这个引导页存在的问题：设计成从接口出发，提高扩展性
    // TODO: 2018/4/30 ParallaxContainer中的内部类会不会引发内存泄漏
    // TODO: 2018/4/30 底部小点通过贝塞尔曲线引入，UIBOOM中的内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);

        //设置引导页的页面
        ParallaxContainer parallaxContainer = findViewById(R.id.parallax_container);
        parallaxContainer.setupChildren(getLayoutInflater()
                            ,R.layout.view_intro_1
                            ,R.layout.view_intro_2
                            ,R.layout.view_intro_3
                            ,R.layout.view_intro_3
                            ,R.layout.view_intro_4
                            ,R.layout.view_intro_5);
    }
}
