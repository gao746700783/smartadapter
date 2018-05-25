package com.smart.adapter.abslistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Description: CommonAdapter
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 *
 * @author che300
 */
public class CommonAdapter<T> extends BaseAdapter implements IAdapter<BaseAdapter> {

    protected Context mContext;
    protected int mLayoutId;
    // protected LayoutInflater mInflater;
    protected List<? super T> mDataList;

    IConverter<? super T> mIConverter;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        // mInflater = LayoutInflater.from(context);
        this.mDataList = datas;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return (T) mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, null, parent, mLayoutId,position);
        mIConverter.convert(holder, (T) mDataList.get(position), position);

        return holder.getConvertView();
    }

    public CommonAdapter<T> bindViewAndData(IConverter<? super T> converter) {
        this.mIConverter = converter;
        return this;
    }

    /**
     * 局部更新数据，调用一次getView()方法；Google推荐的做法
     *
     * @param listView 要更新的 ListView
     * @param position 要更新的位置
     */
    public void notifyDataSetChanged(ListView listView, int position) {
        /* 第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /* 最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /* 在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /* 获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }
    }

    @Override
    public BaseAdapter getAdapter() {
        return this;
    }

    @Override
    public List getDataList() {
        return this.mDataList;
    }

    @Override
    public void setDataList(List list) {
        this.mDataList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public void appendDataList(List list) {
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

}
