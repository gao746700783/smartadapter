package com.smart.adapter.pager.base;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.adapter.pager.multi.MultiItemTypeSupport;
import com.smart.adapter.rvbinding.BaseBindingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: BaseBindingAdapter
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 *
 * @author gaoxiaohui
 */

public class BaseBindingPagerAdapter<T> extends PagerAdapter implements IPagerAdapter<T> {

    protected final String TAG = BaseBindingAdapter.class.getSimpleName();

    protected Context mContext;

    protected LayoutInflater mInflater;
    protected int mLayoutId;
    // protected List<Integer> mLayoutIdList;

    protected ObservableArrayList<T> mDataItems = new ObservableArrayList<>();
    // protected OnDataSetChangedCallback<T> dataChangedCallback;

    protected List<String> mTitleList;

    protected IPagerConverter mIConverter;

    protected BaseBindingPagerAdapter(Context context) {
        this(context, 0);
    }

    protected BaseBindingPagerAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    protected BaseBindingPagerAdapter(Context context, int layoutId, List<T> datas) {
        this(context, layoutId, datas, null);
    }

    protected BaseBindingPagerAdapter(Context context, int layoutId, List<T> datas, List<String> titles) {
        this.mContext = context;
        this.mLayoutId = layoutId;

        this.mInflater = LayoutInflater.from(mContext);

        this.mDataItems = new ObservableArrayList<>();
        //this.dataChangedCallback = new OnDataSetChangedCallback<T>(this,this);
        if (null != datas && datas.size() > 0) {
            this.mDataItems.addAll(datas);
        }

        this.mTitleList = titles;
    }

    public BaseBindingPagerAdapter<T> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    public BaseBindingPagerAdapter<T> list(List<T> datas) {
        this.mDataItems.clear();
        this.mDataItems.addAll(datas);
        return this;
    }

    public BaseBindingPagerAdapter<T> titles(List<String> titles) {
        this.mTitleList = titles;
        return this;
    }

    public BaseBindingPagerAdapter<T> bindViewAndData(IPagerConverter converter) {
        this.mIConverter = converter;
        return this;
    }

    @Override
    public int getCount() {
        if (null == mTitleList) {
            return 0;
        } else {
            return mTitleList.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem container:" + container + ",position:" + position);
        //    // 1. common step
        //    container.addView(mViewList.get(position));
        //    return mViewList.get(position);

        // 2. binding data
        if (mMultiItemTypeSupport != null) {
            int viewType = mMultiItemTypeSupport.getItemType(position);
            int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
            ViewDataBinding binding2 = DataBindingUtil.inflate(mInflater, layoutId, container, false);
            binding2.setVariable(mIConverter.getVariableId(viewType), mIConverter.getBindingData(position, viewType));
            binding2.executePendingBindings();
            if (mContext instanceof LifecycleOwner) {
                binding2.setLifecycleOwner((LifecycleOwner) mContext);
            }
            container.addView(binding2.getRoot());
            return binding2.getRoot();
        }

        // T item = getItem(position);
        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, mLayoutId, container, false);
        binding.setVariable(mIConverter.getVariableId(0), mIConverter.getBindingData(position, 0));
        binding.executePendingBindings();
        if (mContext instanceof LifecycleOwner) {
            binding.setLifecycleOwner((LifecycleOwner) mContext);
        }

        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

//    private T getItem(int position) {
//        return mDataItems.get(position);
//    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null && mTitleList.size() >= position) {
            return mTitleList.get(position);
        }
        return super.getPageTitle(position);
    }

    @Override
    public void resetDataList(List<T> list) {
        if (this.mDataItems != null) {
            this.mDataItems.clear();
            this.mDataItems.addAll(list);
        }
    }

    @Override
    public void resetDataList(ArrayList<T> list) {
        if (this.mDataItems != null) {
            this.mDataItems.clear();
            this.mDataItems.addAll(list);
        }
    }

    @Override
    public void resetDataList(ObservableArrayList<T> newItems) {
        this.mDataItems = newItems;
    }

    /* <!---- multiItem support ----> */
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport = null;

    protected BaseBindingPagerAdapter<T> multiItemTypeSupport(MultiItemTypeSupport<T> multiItemTypeSupport) {
        this.mMultiItemTypeSupport = multiItemTypeSupport;
        return this;
    }
}
