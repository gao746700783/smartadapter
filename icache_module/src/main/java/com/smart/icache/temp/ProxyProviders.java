package com.smart.icache.temp;

import com.smart.icache.SimpleCache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
 * Createdate:    2018-10-19 10:24
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * ${date}        gxh          1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
public final class ProxyProviders implements InvocationHandler {

    public ProxyProviders(SimpleCache.Builder builder, Class<?> providersClass){

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
