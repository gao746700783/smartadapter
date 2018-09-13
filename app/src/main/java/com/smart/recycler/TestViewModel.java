package com.smart.recycler;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * @author Created by Administrator
 * @version 1.0
 * @time 2018/8/19 0019 下午 1:19
 * @description
 * @updateAuthor Administrator
 * @updateDate 2018/8/19 0019 下午 1:19
 * @updateDes
 */
public class TestViewModel extends AndroidViewModel {

    private String[] mDatas = {
            "Adapter: A subclass of RecyclerView.Adapter responsible for providing views that represent items in a data set.",
            "Position: The position of a data item within an Adapter.",
            "Index: The index of an attached child view as used in a call to getChildAt(int). Contrast with Position.",
            "Binding: The process of preparing a child view to display data corresponding to a position within the adapter.",
            "Recycle (view): A view previously used to display data for a specific adapter position may be placed in a cache for later reuse to display the same type of data again later. This can drastically improve performance by skipping initial layout inflation or construction",
            "Scrap (view): A child view that has entered into a temporarily detached state during layout. Scrap views may be reused without becoming fully detached from the parent RecyclerView, either unmodified if no rebinding is required or modified by the adapter if the view was considered dirty.",
            "Dirty (view): A child view that must be rebound by the adapter before being displayed.",
            "hehe",
            "495948",
            "89757",
            "66666"
    };

    // 创建LiveData
    private MutableLiveData<List<String>> testData = new MutableLiveData<>();

    public TestViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<String>> loadDatas() {
        testData.setValue(Arrays.asList(mDatas));
        return testData;
    }

    // 当MyActivity被销毁时，Framework会调用ViewModel的onCleared()
    @Override
    protected void onCleared() {
        Log.e("AccountModel", "==========onCleared()==========");
        super.onCleared();
    }

}
