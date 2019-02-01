package com.smart.icache.temp;

import com.smart.icache.ICache;

import java.util.List;
import java.util.Set;

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
public class SimpleDataProvider implements ICache {

    @Override
    public void evict(String key) {

    }

    @Override
    public void evictAll() {

    }

    @Override
    public List<String> getKeyList() {
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> void put(String key,T clazz) {

    }

}
