package com.smart.adapter.recyclerview.headerfooter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.smart.adapter.recyclerview.IConverter;
import com.smart.adapter.recyclerview.OnItemClickListener;
import com.smart.adapter.recyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: HeaderFooterAdapter
 * <p>
 * User: qiangzhang <br>
 * Date: 2017/8/28 下午1:26 <br>
 */
public class HeaderFooterAdapter<T> extends RecyclerView.Adapter<ViewHolder>
        implements IHeaderFooterAdapter<RecyclerView.Adapter,T> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    protected Context mContext;
    protected int mLayoutId;

    protected List<? super T> mDataList;

    protected IConverter<? super T> mIConverter;
    private OnItemClickListener<T> mOnItemClickListener;

    public HeaderFooterAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    public HeaderFooterAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mLayoutId = layoutId;

        if (null == datas) {
            datas = new ArrayList<>();
        }
        this.mDataList = datas;
    }

    public HeaderFooterAdapter<T> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    public HeaderFooterAdapter<T> list(List<T> datas) {
        this.mDataList = datas;
        return this;
    }

    public HeaderFooterAdapter<T> bindViewAndData(IConverter<? super T> converter) {
        this.mIConverter = converter;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            //return holder;

        } else if (mFootViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            //return holder;
        }

        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId);
        //设置背景
        //viewHolder.itemView.setBackgroundResource(R.drawable.recycler_bg);
        // ?android:attr/selectableItemBackground
        // 添加点击事件
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }

        //convert(holder, (T) mDataList.get(position));
        mIConverter.convert(holder, (T) mDataList.get(position), position);
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

    private int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    // may cause error
    @Override
    public long getItemId(int position) {
        return mDataList.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return super.getItemViewType(position - getHeadersCount());
    }

    public int getRealItemCount() {
        //return super.getItemCount();
        return mDataList.size();
    }

    @Override
    public List<? super T> getDataList() {
        return this.mDataList;
    }

    @Override
    public void setDataList(List<T> list) {
        this.mDataList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public void appendDataList(List<T> list) {
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    //protected abstract void convert(ViewHolder holder, T t);

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            WrapperUtils.onAttachedToRecyclerView(/*this, */recyclerView,
                    new WrapperUtils.SpanSizeCallback() {
                        @Override
                        public int getSpanSize(GridLayoutManager layoutManager,
                                               GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                            int viewType = getItemViewType(position);
                            if (mHeaderViews.get(viewType) != null) {
                                return layoutManager.getSpanCount();
                            } else if (mFootViews.get(viewType) != null) {
                                return layoutManager.getSpanCount();
                            }
                            if (oldLookup != null)
                                return oldLookup.getSpanSize(position);
                            return 1;
                        }
                    });
        } else {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override
    public IHeaderFooterAdapter addHeaderView(View headerView) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, headerView);
        return this;
    }

    @Override
    public IHeaderFooterAdapter removeHeaderView(View headerView) {
        int viewIndex = mHeaderViews.indexOfValue(headerView);
        mHeaderViews.removeAt(viewIndex);
        return this;
    }

    @Override
    public IHeaderFooterAdapter addFooterView(View footerView) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, footerView);
        return this;
    }

    @Override
    public IHeaderFooterAdapter removeFooterView(View footerView) {
        int viewIndex = mFootViews.indexOfValue(footerView);
        mFootViews.removeAt(viewIndex);
        return this;
    }

}
