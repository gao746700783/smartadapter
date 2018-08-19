package com.smart.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: CommonAdapter
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 * @author che300
 */
public class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>
        implements IAdapter<RecyclerView.Adapter> {

    protected Context mContext;
    protected int mLayoutId;

    protected List<? super T> mDataList;

    protected IConverter<? super T> mIConverter;

    public CommonAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mLayoutId = layoutId;

        if (null == datas){
            datas = new ArrayList<>();
        }
        this.mDataList = datas;
    }

    public CommonAdapter<T> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    public CommonAdapter<T> list(List<T> datas ) {
        this.mDataList = datas;
        return this;
    }

    public CommonAdapter<T> bindViewAndData(IConverter<? super T> converter) {
        this.mIConverter = converter;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId);
        //设置背景
        //viewHolder.itemView.setBackgroundResource(R.drawable.recycler_bg);
        // ?android:attr/selectableItemBackground

        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    /**
     * is or not enabled
     * need to override in SectionAdapter
     *
     * @param viewType viewType
     * @return true for default
     */
    protected boolean isEnabled(int viewType) {
        return true;
    }

    /**
     * set click listener
     *
     * @param parent     parent
     * @param viewHolder viewHolder
     * @param viewType   viewType
     */
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }
        View mConvertView = viewHolder.getConvertView();
        if (null == mConvertView) {
            return;
        }

        mConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
//                    if (mHasHeaderOrFooter) {
//                        position -= 1;
//                    }
                    mOnItemClickListener.onItemClick(parent, v, (T) mDataList.get(position), position);
                }
            }
        });

        mConvertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
//                    if (mHasHeaderOrFooter) {
//                        position -= 1;
//                    }
                    return mOnItemClickListener.onItemLongClick(parent, v, (T) mDataList.get(position),
                            position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //convert(holder, (T) mDataList.get(position));
        mIConverter.convert(holder,(T) mDataList.get(position),position);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    // may cause error
    @Override
    public long getItemId(int position) {
        return mDataList.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public List getDataList() {
        return this.mDataList;
    }

    @Override
    public void appendDataList(List list) {
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void setDataList(List list) {
        this.mDataList = list;
        this.notifyDataSetChanged();
    }

    //protected abstract void convert(ViewHolder holder, T t);

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
