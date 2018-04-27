package com.sixin.im.widget.fancytextview;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 字体缓存类
 * 为了避免重复调用getAssets方法而损耗性能提出的解决方案
 */
public class FontCache {

    private static Map<String, Typeface> sFontCache = new HashMap<>();

    private FontCache(){
        throw new UnsupportedOperationException("you can't init it");
    }

    /**
     *获取Typeface的方法
     * @param fontName 字体文件的名称 不可为空
     * @param context 上下文对象   不可为空
     * @return typeface 可能为空
     */
    public static Typeface getTypeface(@NonNull String fontName,@NonNull Context context){
        Typeface typeface = sFontCache.get(fontName);
        if (typeface == null) {
            String fontPath = "fonts"+ File.separator+fontName+".TTF";
            try{
//                if(context instanceof Application){
//                    typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
//                }else{
                    typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
//                }
            }catch(Exception e){
                return null;
            }
            sFontCache.put(fontName, typeface);
        }
        return typeface;
    }

}
