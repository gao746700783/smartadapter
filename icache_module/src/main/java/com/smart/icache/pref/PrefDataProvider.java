package com.smart.icache.pref;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.smart.icache.ICache;
import com.smart.icache.utils.JsonUtils;
import com.smart.icache.utils.PreferenceUtils;

import java.util.ArrayList;
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
public class PrefDataProvider implements ICache {

    private Context mCtx;

    private List<String> mKeyList = new ArrayList<>();

    public PrefDataProvider(Context context) {
        mCtx = context;
    }

    @Override
    public void evict(String key) {
        if (PreferenceUtils.hasKey(mCtx, key)) {
            PreferenceUtils.clearPreference(mCtx, key);
        }
        mKeyList.remove(key);
    }

    @Override
    public void evictAll() {
        PreferenceUtils.clearPreference(mCtx);
        mKeyList.clear();
    }

    @Override
    public List<String> getKeyList() {
        return mKeyList;
    }

    @Override
    public <T> void put(String key, T clazz) {
        String value = JsonUtils.toJson(clazz);
        PreferenceUtils.setPrefString(mCtx, key, value);

        mKeyList.add(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String value = PreferenceUtils.getPrefString(mCtx, key, "");
        return JsonUtils.fromJson(value, TypeToken.get(clazz));
    }

}
