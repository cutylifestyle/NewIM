package com.sixin.im.view.activities;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.sixin.im.IMApp;
import com.sixin.im.R;
import com.sixin.im.util.activityutil.ActivityUtils;
import com.sixin.im.view.activities.base.RxBaseActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends RxBaseActivity {

    private AnimatedVectorDrawable mAnimatedVectorDrawable;
    private AnimatedVectorDrawableCompat mAnimatedVectorDrawableCompat;
    private ImageView mSplashImg;
    private TextView mSplashTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2018/5/28 集成bugly
        // TODO: 2018/5/28 implementation 'com.trello.rxlifecycle2:rxlifecycle-navi:2.2.1'的作用
        // TODO: 2018/5/28 添加权限工具类申请读写存储权限
        // TODO: 2018/4/9 自定义view，修改字体
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setBackgroundDrawable(null);

        mSplashImg = findViewById(R.id.splash_img);
        mSplashTv = findViewById(R.id.splash_tv);


        Observable<Boolean> loginObservable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter){
                if (EMClient.getInstance().isLoggedInBefore()) {
                    // TODO: 2018/5/28 获取用户信息是否需要连接网络，EMClient.getInstance.isLoggedInBefore是否需要连接网络
                    emitter.onNext(true);
                }else{
                    emitter.onNext(false);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<Boolean> animationObservable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter){
                doAnimation(emitter);
            }
        });

        Observable.zip(loginObservable, animationObservable, new BiFunction<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(Boolean loginBoolean, Boolean animationBoolean){
                return loginBoolean && animationBoolean;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Boolean>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean result) {
                        toHomeOrLogin(result);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 判断是跳转到Home页面还是Login页面
     * @param result true:跳转到HOME页面，false跳转到Login页面
     */
    private void toHomeOrLogin(boolean result) {
        if (result) {
            Intent intent = new Intent(this, HomeActivity.class);
            ActivityUtils.startActivity(intent);
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            ActivityUtils.startActivity(intent);
        }
        finish();
    }

    /**
     * 执行页面渲染动画
     * @param emitter RxJava中的发射器
     */
    private void doAnimation(final ObservableEmitter<Boolean> emitter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mAnimatedVectorDrawable = (AnimatedVectorDrawable) mSplashImg.getDrawable();
            mAnimatedVectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        mSplashTv.setVisibility(View.VISIBLE);
                        emitter.onNext(true);
                        emitter.onComplete();
                    }
                });
            mAnimatedVectorDrawable.start();
        }else{
            mAnimatedVectorDrawableCompat = (AnimatedVectorDrawableCompat) mSplashImg.getDrawable();
            mAnimatedVectorDrawableCompat.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        mSplashTv.setVisibility(View.VISIBLE);
                        emitter.onNext(true);
                        emitter.onComplete();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IMApp.getRefWatcher(getApplicationContext()).watch(this);
    }
}
