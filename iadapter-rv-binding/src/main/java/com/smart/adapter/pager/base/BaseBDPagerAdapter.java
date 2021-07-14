package com.smart.adapter.pager.base;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.adapter.rvbinding.BaseBindingAdapter;

import java.util.List;

/**
 * Description: BaseBDPagerAdapter
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 *
 * @author gaoxiaohui
 */

public class BaseBDPagerAdapter extends PagerAdapter {

    protected final String TAG = BaseBindingAdapter.class.getSimpleName();

    protected Context mContext;

    protected LayoutInflater mInflater;
    protected List<Integer> mLayoutList;

    protected List<String> mTitleList;

    protected IPagerConverter mIConverter;

    public BaseBDPagerAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public BaseBDPagerAdapter(Context context, List<Integer> layouts, List<String> titles) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutList = layouts;
        this.mTitleList = titles;
    }

    public BaseBDPagerAdapter layouts(List<Integer> layouts) {
        this.mLayoutList = layouts;
        return this;
    }

    public BaseBDPagerAdapter titles(List<String> titles) {
        this.mTitleList = titles;
        return this;
    }

    public BaseBDPagerAdapter bindViewAndData(IPagerConverter converter) {
        this.mIConverter = converter;
        return this;
    }

    @Override
    public int getCount() {
        if (null == mLayoutList) {
            return 0;
        } else {
            return mLayoutList.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int layoutId = mLayoutList.get(position);
        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, layoutId, container, false);
        binding.setVariable(mIConverter.getVariableId(position), mIConverter.getBindingData(position, 0));
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

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null && mTitleList.size() >= position) {
            return mTitleList.get(position);
        }
        return super.getPageTitle(position);
    }

}
