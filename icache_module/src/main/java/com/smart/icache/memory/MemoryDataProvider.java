package com.smart.icache.memory;

import android.support.v4.util.LruCache;

import com.google.gson.reflect.TypeToken;
import com.smart.icache.ICache;
import com.smart.icache.utils.JsonUtils;
import com.smart.icache.utils.PreferenceUtils;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Project name:  smartrecyclerview-master
 * Package name:  com.smart.icache
 * Description:   ${todo}(用一句话描述该文件做什么)
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company        che300.
 *
 * @author che300
 * @version V1.0
 * Createdate:    2018-10-19 10:48
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * ${date}        gxh          1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
public class MemoryDataProvider implements ICache {

    private LruCache<String, String> mMemoryCache;
//    private LruCache<String,Bitmap> mMemoryBitmapCache;

    public MemoryDataProvider() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;

//        mMemoryBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
//            @Override
//            protected int sizeOf(String key, Bitmap value) {
//                return value.getHeight() * value.getRowBytes();
//            }
//        };

        mMemoryCache = new LruCache<String, String>(cacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                Charset defCharset = Charset.defaultCharset();
                return key.getBytes(defCharset).length + value.getBytes(defCharset).length;
            }
        };
    }

    @Override
    public void evict(String key) {
        mMemoryCache.remove(key);
    }

    @Override
    public void evictAll() {
        mMemoryCache.evictAll();
    }

    @Override
    public List<String> getKeyList() {
        // mMemoryCache.snapshot().keySet();

        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String value = mMemoryCache.get(key);
        return JsonUtils.fromJson(value, TypeToken.get(clazz));
    }

    @Override
    public <T> void put(String key, T clazz) {
        String value = JsonUtils.toJson(clazz);
        mMemoryCache.put(key,value);
    }

}
