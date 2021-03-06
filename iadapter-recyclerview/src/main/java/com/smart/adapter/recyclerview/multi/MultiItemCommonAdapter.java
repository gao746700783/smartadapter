package com.smart.adapter.recyclerview.multi;

import android.content.Context;
import android.view.ViewGroup;

import com.smart.adapter.recyclerview.BaseAdapter;
import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.ViewHolder;

import java.util.List;

/**
 * @author che300
 */
public class MultiItemCommonAdapter<T> extends BaseAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas,
                                  MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;

        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position, (T)mDataList.get(position));
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiItemTypeSupport == null) return super.onCreateViewHolder(parent, viewType);

        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        ViewHolder holder = ViewHolder.get(mContext, null, parent, layoutId);
        setListener(parent, holder, viewType);
        return holder;
    }

}
