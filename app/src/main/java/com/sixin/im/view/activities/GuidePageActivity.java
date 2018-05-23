package com.sixin.im.view.activities;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.sixin.im.Constant;
import com.sixin.im.R;
import com.sixin.im.util.activityutil.ActivityUtils;
import com.sixin.im.util.sharedpreferencesutil.IPreference;
import com.sixin.im.widget.parallaxpager.ParallaxContainer;

public class GuidePageActivity extends Activity {
    // TODO: 2018/5/17 设定动画的值存在问题
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

        //判断是否直接跳转到欢迎页面
        final IPreference preference = IPreference.prefHolder.getPreference(this,Constant.SP_GUIDE_PAGE);
        boolean showed =preference.get(Constant.SHOWED, IPreference.DataType.BOOLEAN);
        if(showed){
            //跳转到欢迎页面
            startActivity(SplashActivity.class);
            return;
        }

        //设置引导页的页面
        ParallaxContainer parallaxContainer = findViewById(R.id.parallax_container);
        parallaxContainer.setupChildren(getLayoutInflater()
                            ,R.layout.view_intro_1
                            ,R.layout.view_intro_2
                            ,R.layout.view_intro_3
                            ,R.layout.view_intro_4
                            ,R.layout.view_intro_5);
        Button btnIn = parallaxContainer.findViewById(R.id.btn_in);
        if(btnIn != null){
            btnIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preference.put(Constant.SHOWED,true);
                    startActivity(SplashActivity.class);
                }
            });
        }
    }

    private void startActivity(Class<?> clazz){
        ActivityUtils.startActivity(clazz);
        finish();
    }
}
