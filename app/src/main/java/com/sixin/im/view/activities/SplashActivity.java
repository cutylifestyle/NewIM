package com.sixin.im.view.activities;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixin.im.R;


public class SplashActivity extends AppCompatActivity {

    private AnimatedVectorDrawable mAnimatedVectorDrawable;
    private AnimatedVectorDrawableCompat mAnimatedVectorDrawableCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2018/4/25 内存泄漏测试
        // TODO: 2018/4/11 这块的逻辑用RxJava来写
        // TODO: 2018/4/9 自定义view，修改字体
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setBackgroundDrawable(null);

        ImageView splashImg = findViewById(R.id.splash_img);
        final TextView splashTv = findViewById(R.id.splash_tv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mAnimatedVectorDrawable = (AnimatedVectorDrawable) splashImg.getDrawable();
            mAnimatedVectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        splashTv.setVisibility(View.VISIBLE);
                    }
                });
            mAnimatedVectorDrawable.start();
        }else{
            mAnimatedVectorDrawableCompat = (AnimatedVectorDrawableCompat) splashImg.getDrawable();
            mAnimatedVectorDrawableCompat.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        splashTv.setVisibility(View.VISIBLE);
                    }
            });
            mAnimatedVectorDrawableCompat.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAnimatedVectorDrawable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mAnimatedVectorDrawable.clearAnimationCallbacks();
            }
            mAnimatedVectorDrawable.stop();
        }
        if (mAnimatedVectorDrawableCompat != null) {
            mAnimatedVectorDrawableCompat.clearAnimationCallbacks();
            mAnimatedVectorDrawableCompat.stop();
        }
    }
}
