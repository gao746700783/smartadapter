package com.smart.icache;

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
 * Createdate:    2018-10-18 14:20
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * ${date}        gxh          1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
public interface ICache {

    //根据key驱逐缓存的特定数据
    void evict(String key);

    //驱逐缓存的所有数据
    void evictAll();

    List<String> getKeyList();

    //保存对象数据
    <T> void put(String key, T clazz);

    //根据key取出数据
    <T> T get(String key, Class<T> clazz);

}
