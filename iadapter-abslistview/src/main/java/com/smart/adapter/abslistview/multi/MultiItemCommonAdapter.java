package com.smart.adapter.abslistview.multi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.smart.adapter.abslistview.CommonAdapter;
import com.smart.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: MultiItemCommonAdapter
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 *
 * @author che300
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas,
                                  MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getViewTypeCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position, (T) mDataList.get(position));
        return super.getItemViewType(position);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mMultiItemTypeSupport == null)
            return super.getView(position, convertView, parent);

        int layoutId = mMultiItemTypeSupport.getLayoutId(position, getItem(position));

        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        //mIConverter.convert(holder, (T) mDataList.get(position), position);

        return holder.getConvertView();
    }

}
