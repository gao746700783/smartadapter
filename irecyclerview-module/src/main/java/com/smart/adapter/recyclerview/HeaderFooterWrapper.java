package com.smart.adapter.recyclerview;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description: HeaderFooterWrapper
 * <p>
 * by hongyang csdn article:
 * http://blog.csdn.net/lmj623565791/article/details/51854533
 * User: qiangzhang <br>
 * Date: 2017/8/28 下午1:26 <br>
 */
public class HeaderFooterWrapper<T> extends RecyclerView.Adapter<ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private CommonAdapter<T> mInnerAdapter;

    public HeaderFooterWrapper(CommonAdapter<T> adapter) {
        mInnerAdapter = adapter;
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
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    //    @Override
    //    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    //        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
    //            @Override
    //            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
    //                int viewType = getItemViewType(position);
    //                if (mHeaderViews.get(viewType) != null) {
    //                    return layoutManager.getSpanCount();
    //                } else if (mFootViews.get(viewType) != null) {
    //                    return layoutManager.getSpanCount();
    //                }
    //                if (oldLookup != null)
    //                    return oldLookup.getSpanSize(position);
    //                return 1;
    //            }
    //        });
    //    }
    //
    //    @Override
    //    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    //        mInnerAdapter.onViewAttachedToWindow(holder);
    //        int position = holder.getLayoutPosition();
    //        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
    //            WrapperUtils.setFullSpan(holder);
    //        }
    //    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }


    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }
}
