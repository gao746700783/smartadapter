package com.smart.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description: CommonAdapter
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDataList;
    protected LayoutInflater mInflater;

    // add by me
    protected boolean mHasHeaderOrFooter;

    private OnItemClickListener<T> mOnItemClickListener;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        this(context, layoutId, datas, false);
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas, boolean isHasHeaderOrFooter) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
        this.mDataList = datas;
        this.mHasHeaderOrFooter = isHasHeaderOrFooter;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        //设置背景
        //viewHolder.itemView.setBackgroundResource(R.drawable.recycler_bg);
        // ?android:attr/selectableItemBackground

        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (mHasHeaderOrFooter) {
                        position -= 1;
                    }
                    mOnItemClickListener.onItemClick(
                            parent, v, mDataList.get(position), position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (mHasHeaderOrFooter) {
                        position -= 1;
                    }
                    return mOnItemClickListener.onItemLongClick(
                            parent, v, mDataList.get(position), position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDataList.get(position));
    }

    protected abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}
