package com.sixin.im.widget.fancytextview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 支持自定义字体的textView
 * todo 这个类如何使用的说明
 */
public class FancyTextView extends android.support.v7.widget.AppCompatTextView {

    public FancyTextView(Context context) {
        super(context);

    }

    public FancyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = FontCache.getTypeface("华文彩云", context);
        if (typeface != null) {
            setTypeface(typeface);
        }
    }

    public FancyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
