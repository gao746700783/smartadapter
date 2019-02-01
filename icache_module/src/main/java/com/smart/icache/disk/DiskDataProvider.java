package com.smart.icache.disk;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.google.gson.reflect.TypeToken;
import com.jakewharton.disklrucache.DiskLruCache;
import com.smart.icache.ICache;
import com.smart.icache.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
public class DiskDataProvider implements ICache {

    private DiskLruCache mCache;
    private SafeKeyGenerator mSafeKeyGenerator = new SafeKeyGenerator();

    private Context mCtx;
    public static final long MAX_SIZE = 20 * 1024 *1024;

    public DiskDataProvider(Context context,int version){
        this(context,null,version,MAX_SIZE);
    }

    public DiskDataProvider(Context context, File dir, int version, long maxSize) {
        mCtx = context;

        if (null == dir) {
            dir = mCtx.getCacheDir();
        }

        try {
            mCache = DiskLruCache.open(dir, version, 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void evict(String key) {
        try {
            mCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void evictAll() {
        try {
            mCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getKeyList() {
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String value = getString(key);
        return JsonUtils.fromJson(value, TypeToken.get(clazz));
    }

    @Override
    public <T> void put(String key, T clazz) {
        if (clazz instanceof InputStream) {
            try {
                putInputStreamOrThrow(key, (InputStream) clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String value = JsonUtils.toJson(clazz);
            putString(key, value);
        }
    }

    private DiskLruCache.Snapshot getOrThrow(String key) throws IOException, NullPointerException {
        DiskLruCache.Snapshot snapshot = mCache.get(mSafeKeyGenerator.getSafeKey(key));
        if (snapshot == null) {
            throw new NullPointerException("DiskLruCache.get() returned null");
        }
        return snapshot;
    }

    private InputStream getInputStream(String key) {
        try {
            return getOrThrow(key).getInputStream(0);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getString(String key) {
        try {
            return getOrThrow(key).getString(0);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private DiskLruCache.Editor editOrThrow(String key) throws IOException, NullPointerException {
        DiskLruCache.Editor editor = mCache.edit(mSafeKeyGenerator.getSafeKey(key));
        if (editor == null) {
            throw new NullPointerException("DiskLruCache.edit() returned null");
        }
        return editor;
    }

    private void putInputStreamOrThrow(String key, InputStream inputStream) throws IOException, NullPointerException {
        DiskLruCache.Editor editor = editOrThrow(key);
        try {
            byte[] bs = new byte[1024];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                editor.newOutputStream(0).write(bs, 0, len);
            }
            editor.commit();
        } finally {
            editor.abortUnlessCommitted();
        }
    }

    private void putBytesOrThrow(String key, byte[] value) throws IOException, NullPointerException {
        DiskLruCache.Editor editor = editOrThrow(key);
        try {
            editor.newOutputStream(0).write(value);
            editor.commit();
        } finally {
            editor.abortUnlessCommitted();
        }
    }

    private void putBytes(String key, byte[] value) {
        try {
            putBytesOrThrow(key, value);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void putString(String key, String value) {
        try {
            putBytesOrThrow(key, value.getBytes(Charset.defaultCharset()));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static class SafeKeyGenerator {

        private final LruCache<String, String> loadIdToSafeHash = new LruCache<>(100);

        public String getSafeKey(String key) {
            String safeKey;
            synchronized (loadIdToSafeHash) {
                safeKey = loadIdToSafeHash.get(key);
            }
            if (safeKey == null) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    messageDigest.update(key.getBytes(Charset.defaultCharset()));
                    safeKey = bytesToHexString(messageDigest.digest());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                synchronized (loadIdToSafeHash) {
                    loadIdToSafeHash.put(key, safeKey);
                }
            }
            return safeKey;
        }

        private static String bytesToHexString(byte[] bytes) {
            // http://stackoverflow.com/questions/332079
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        }

    }

}
