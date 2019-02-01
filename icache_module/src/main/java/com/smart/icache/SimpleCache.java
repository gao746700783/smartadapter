package com.smart.icache;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.smart.icache.disk.DiskDataProvider;
import com.smart.icache.memory.MemoryDataProvider;
import com.smart.icache.pref.PrefDataProvider;

import java.io.File;

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
 * Createdate:    2018-10-18 14:47
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * ${date}        gxh          1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
public class SimpleCache {

    private static SimpleCache instance = null;

    private SimpleCache() {
    }

    public static SimpleCache getInstance() {
        if (instance == null) {
            synchronized (SimpleCache.class) {
                if (instance == null) {
                    instance = new SimpleCache();
                }
            }
        }
        return instance;
    }

    private ICache iCacheProvider;

    private ArrayMap<String, ICache> iCacheArrayMap = new ArrayMap<>();

    private void putCacheProvider(CacheMode cacheMode, ICache cacheProvider) {
        String cacheModeKey = getModelName(cacheMode);
        iCacheArrayMap.put(cacheModeKey, cacheProvider);
    }

    private ICache getCacheProvider(CacheMode cacheMode) {
        String cacheModeKey = getModelName(cacheMode);
        if (iCacheArrayMap.containsKey(cacheModeKey)) {
            return iCacheArrayMap.get(cacheModeKey);
        }
        return null;
    }

    public <T> T get(String key, Class<T> clazz) {
        return iCacheProvider.get(key, clazz);
    }

    public <T> void put(String key, T obj) {
        iCacheProvider.put(key, obj);
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public static class Builder {

        // cache path
        // cache type : simpleString  File缓存，SP缓存，或者数据库缓存
        //              普通的字符串、JsonObject、JsonArray、Bitmap、Drawable、序列化的java对象，和 byte数据。
        // cache key prefix:
        // cache mode
        // 缓存占用空间大小  动态空间分配   缓存个数  缓存时间

        Context context;

        public Builder(Context context) {
            this.context = context;
        }

        String cachePath;

        public Builder cachePath(String path) {
            cachePath = path;
            return this;
        }

        String cacheKeyPrefix;

        public Builder cacheKeyPrefix(String keyPrefix) {
            cacheKeyPrefix = keyPrefix;
            return this;
        }

        CacheMode cacheMode;

        public Builder cacheMode(CacheMode mode) {
            cacheMode = mode;
            return this;
        }

        public SimpleCache build() {
//            if (TextUtils.isEmpty(cachePath)) {
//                cachePath = context.getCacheDir().getPath();
//            }

            if (TextUtils.isEmpty(cacheKeyPrefix)) {
                cacheKeyPrefix = SimpleCache.class.getSimpleName();
            }

            if (null == cacheMode) {
                cacheMode = CacheMode.PREF;
            }

            SimpleCache simpleCacheInstance = SimpleCache.getInstance();

            ICache cacheProvider = simpleCacheInstance.getCacheProvider(cacheMode);
            if (null == cacheProvider) {
                ICache cacheProviderImpl;
                if (cacheMode == CacheMode.PREF) {
                    cacheProviderImpl = new PrefDataProvider(context);
                } else if (cacheMode == CacheMode.DISK) {
                    if (TextUtils.isEmpty(cachePath)) {
                        cacheProviderImpl = new DiskDataProvider(context, 1);
                    } else {
                        cacheProviderImpl = new DiskDataProvider(context, new File(cachePath), 1, DiskDataProvider.MAX_SIZE);
                    }
                } else if (cacheMode == CacheMode.MEMORY) {
                    cacheProviderImpl = new MemoryDataProvider();
                } else {
                    if (TextUtils.isEmpty(cachePath)) {
                        cacheProviderImpl = new DiskDataProvider(context, 1);
                    } else {
                        cacheProviderImpl = new DiskDataProvider(context, new File(cachePath), 1, DiskDataProvider.MAX_SIZE);
                    }
                }

                simpleCacheInstance.putCacheProvider(cacheMode, cacheProviderImpl);

                cacheProvider = cacheProviderImpl;
            }

            simpleCacheInstance.iCacheProvider = cacheProvider;

            return simpleCacheInstance;
        }

    }

    private String getModelName(CacheMode cacheMode) {
        if (cacheMode == CacheMode.PREF) {
            return "pref";
        } else if (cacheMode == CacheMode.MEMORY) {
            return "memory";
        } else if (cacheMode == CacheMode.DISK) {
            return "disk";
        } else if (cacheMode == CacheMode.DOUBLE) {
            return "double";
        }

        return null;
    }

    public enum CacheMode {
        PREF,    // preference
        MEMORY,  // memory
        DISK,    // disk
        DOUBLE,  // memory and disk
    }

    //    private Builder builder;
    //    private ProxyProviders proxyProviders;
    //
    //    private SimpleCache(Builder builder) {
    //        this.builder = builder;
    //    }
    //
    //    public <T> T using(final Class<T> classProviders) {
    //        proxyProviders = new ProxyProviders(builder, classProviders);
    //
    //        return (T) Proxy.newProxyInstance(classProviders.getClassLoader(),
    //                new Class<?>[]{classProviders},
    //                proxyProviders);
    //    }
    //
    //    //    public Observable<Void> evictAll() {
    //    //        return proxyProviders.evictAll();
    //    //    }
    //
    //    /**
    //     * Builder for building an specific SimpleCache instance
    //     */
    //    public static class Builder {
    //
    //    }


}
