package com.smart.recycler.modules.swipe

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log

/**
 * Project name:  smartrecyclerview-master
 * Package name:  com.smart.recycler.modules.swipe
 * Description:   ${todo}(用一句话描述该文件做什么)
 * Copyright:     Copyright(C) 2017-2018
 *                All rights Reserved, Designed By gaoxiaohui
 * Company
 *
 * @author  che300
 * @version V1.0
 *          Createdate:    2018-09-13-11:13
 *          <p>
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          ${date}       che300         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 */
class SimpleLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        Log.d(TAG, "onCreated: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d(TAG, "onStart: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(TAG, "onResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(TAG, "onPause: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.d(TAG, "onStop: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun customMethod() {
        Log.d(TAG, "customMethod: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny() {//此方法可以有参数，但类型必须如两者之一(LifecycleOwner owner,Lifecycle.Event event)
        Log.d(TAG, "onAny: ");
    }

    companion object {
        val TAG: String = "SimpleLifecycleObserver"
    }
}