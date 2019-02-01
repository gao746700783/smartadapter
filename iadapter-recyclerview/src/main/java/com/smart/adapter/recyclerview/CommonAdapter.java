package com.smart.adapter.recyclerview;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.smart.adapter.recyclerview.headerfooter.IHeaderFooterAdapter;
import com.smart.adapter.recyclerview.headerfooter.WrapperUtils;
import com.smart.adapter.recyclerview.multi.MultiItemTypeSupport;

import java.util.List;

/**
 * Description: CommonAdapter extends BaseAdapter
 * 扩展特性
 * 1. HeaderFooter
 * 2. MultiItemSupport
 * 3.
 *
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 *
 * @author che300
 */
public class CommonAdapter<T> extends BaseAdapter<T>
        implements IHeaderFooterAdapter<RecyclerView.Adapter> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    public CommonAdapter<T> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    public CommonAdapter<T> list(List<T> datas) {
        this.mDataList = datas;
        return this;
    }

    public CommonAdapter<T> bindViewAndData(IConverter<? super T> converter) {
        this.mIConverter = converter;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));

        } else if (mFootViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
        }

        if (mMultiItemTypeSupport != null) {
            int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
            ViewHolder holder = ViewHolder.get(mContext, null, parent, layoutId);
            setListener(parent, holder, viewType);
            return holder;
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }

        mIConverter.convert(holder, getItem(position), position);
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

    @Override
    public T getItem(int position) {
        // position < headerView 数目
        if (position < getHeadersCount()) {
            return null;
        }

        // reset position
        position = position - getHeadersCount();
        return super.getItem(position);
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

        // reset position
        position = position - getHeadersCount();
        if (mMultiItemTypeSupport != null) {
            return mMultiItemTypeSupport.getItemViewType(position, getItem(position));
        }
        return super.getItemViewType(position);
    }

    public int getRealItemCount() {
        return mDataList.size();
    }

    @Override
    public List getDataList() {
        return this.mDataList;
    }

    @Override
    public void setDataList(java.util.List list) {
        this.mDataList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public void appendDataList(java.util.List list) {
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
    public CommonAdapter<T> addHeaderView(View headerView) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, headerView);
        return this;
    }

    @Override
    public CommonAdapter<T> removeHeaderView(View headerView) {
        int viewIndex = mHeaderViews.indexOfValue(headerView);
        mHeaderViews.removeAt(viewIndex);
        return this;
    }

    @Override
    public CommonAdapter<T> addFooterView(View footerView) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, footerView);
        return this;
    }

    @Override
    public CommonAdapter<T> removeFooterView(View footerView) {
        int viewIndex = mFootViews.indexOfValue(footerView);
        mFootViews.removeAt(viewIndex);
        return this;
    }

    /* <!---- multiItem support ----*/
    private MultiItemTypeSupport<T> mMultiItemTypeSupport = null;

    public CommonAdapter<T> multiItemTypeSupport(MultiItemTypeSupport<T> multiItemTypeSupport) {
        this.mMultiItemTypeSupport = multiItemTypeSupport;
        return this;
    }

}
