//package com.smart.recycler.modules.swipe
//
//import android.arch.lifecycle.MutableLiveData
//import android.app.Application
//import android.arch.lifecycle.AndroidViewModel
//import android.util.Log
//
//
///**
// * @author Created by Administrator
// * @time 2018/8/19 0019 下午 1:17
// * @description
// * @version 1.0
// * @updateAuthor Administrator
// * @updateDate 2018/8/19 0019 下午 1:17
// * @updateDes
// */
//class TestViewModel(application: Application) : AndroidViewModel(application) {
//
//    // 创建LiveData
//    val account: MutableLiveData<AccountBean> = MutableLiveData<T>()
//
//    fun setAccount(name: String, phone: String, blog: String) {
//        account.setValue(AccountBean(name, phone, blog))
//    }
//
//    // 当MyActivity被销毁时，Framework会调用ViewModel的onCleared()
//    override fun onCleared() {
//        Log.e("AccountModel", "==========onCleared()==========")
//        super.onCleared()
//    }
//}