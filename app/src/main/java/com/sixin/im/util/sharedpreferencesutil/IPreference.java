package com.sixin.im.util.sharedpreferencesutil;

import android.content.Context;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IPreference {
    IPreferenceHolder prefHolder = new IPreferenceHolder();

    /**
     * IPreference的持有类
     */
    class IPreferenceHolder {
        /**
         * 获得一个新创建的IPreference对象
         * @param context 上下文对象
         * @param fileName 文件名
         * @return
         */
        public IPreference newPreference(Context context, String fileName){
            return new PreferenceImpl(context, fileName);
        }

        /**
         * 获取一个IPreference对象
         * @param context 上下文对象
         * @return
         */
        public IPreference getPreference(Context context){
            return PreferenceImpl.getPreference(context);
        }

        /**
         * 获取一个IPreference对象
         * @param context 上下文对象
         * @param fileName 文件名
         * @return
         */
        public IPreference getPreference(Context context, String fileName){
            return PreferenceImpl.getPreference(context, fileName);
        }
    }

    /**
     * 保存一个数据
     * @param key 键
     * @param value 值
     */
    <T> void put(String key, T value);

    /**
     * 保存一个Map集合
     * @param map Map集合
     */
    <T> void putAll(Map<String, T> map);

    /**
     * 保存一个List集合
     * @param key 键
     * @param list 值
     */
    void putAll(String key, List<String> list);

    /**
     * 保存一个List集合，并且自定保存顺序
     * @param key 键
     * @param list 值
     * @param comparator 比较器
     */
    void putAll(String key, List<String> list, Comparator<String> comparator);

    /**
     * 根据key取出一个数据
     * @param key 键
     */
    <T> T get(String key, DataType type);

    /**
     * 取出全部数据
     */
    Map<String, ?> getAll();

    /**
     * 取出一个List集合
     * @param key 键
     * @return List<String>集合
     */
    List<String> getAll(String key);

    /**
     * 移除一个数据
     * @param key 键
     */
    void remove(String key);

    /**
     * 移除一个集合的数据
     * @param keys 键的集合
     */
    void removeAll(List<String> keys);

    /**
     * 移除一个集合的数据
     * @param keys 键的数组
     */
    void removeAll(String[] keys);

    /**
     * 是否存在key
     * @param key 键
     * @return boolean
     */
    boolean contains(String key);

    /**
     * 清除全部数据
     */
    void clear();

    /**
     * 获取String类型的数据
     * @param key 键
     * @return String
     */
    String getString(String key);

    /**
     * 获取Float类型的数据
     * @param key 键
     * @return float
     */
    float getFloat(String key);

    /**
     * 获取int类型的数据
     * @param key 键
     * @return int
     */
    int getInteger(String key);

    /**
     * 获取long类型的数据
     * @param key 键
     * @return long
     */
    long getLong(String key);

    /**
     * 获取Set类型的数据
     * @param key 键
     * @return Set<String>
     */
    Set<String> getSet(String key);

    /**
     * 获取boolean类型的数据
     * @param key 键
     * @return boolean
     */
    boolean getBoolean(String key);
    /**
     * 枚举：存储或取出的数据类型
     */
    enum DataType {
        INTEGER, LONG, BOOLEAN, FLOAT, STRING, STRING_SET
    }
}
