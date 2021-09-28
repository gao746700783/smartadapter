package com.smart.adapter.rvbinding.advances;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.smart.adapter.rvbinding.BindingViewHolder;
import com.smart.adapter.rvbinding.IConverter;
import com.smart.adapter.rvbinding.anim.AlphaInAnimation;
import com.smart.adapter.rvbinding.anim.base.BaseAnimation;
import com.smart.adapter.rvbinding.headerfooter.IHeaderFooterAdapter;
import com.smart.adapter.rvbinding.headerfooter.WrapperUtils;
import com.smart.adapter.rvbinding.multi.MultiItemTypeSupport;

import java.util.List;

/**
 * Description: CommonAdapter extends BaseAdapter
 * 扩展特性
 * 1. HeaderFooter        -> √
 * 2. multiItem support   -> ×
 * 3. animation support   -> √
 * 4. drag n drop support -> ×
 * 5.
 *
 * <p>
 * User: gxh
 * Date: 2017/6/23 上午11:22
 *
 * @author gaoxiaohui
 */
public class CBindingDiffAdapter<T> extends BaseBindingDiffAdapter<T> implements IHeaderFooterAdapter<T> {

    private static final String TAG = "CommonBindingAdapter";

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private final SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private final SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    public CBindingDiffAdapter(Context context) {
        super(context);
    }

    public CBindingDiffAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public CBindingDiffAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public CBindingDiffAdapter<T> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    @Override
    public CBindingDiffAdapter<T> list(List<T> datas,DiffUtil.ItemCallback<T> itemCallback) {
        super.list(datas,itemCallback);
        return this;
    }

    @Override
    public CBindingDiffAdapter<T> bindViewAndData(IConverter<? super T> converter) {
        this.mIConverter = converter;
        return this;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return BindingViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));

        } else if (mFootViews.get(viewType) != null) {
            return BindingViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
        }

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            WrapperUtils.onAttachedToRecyclerView(recyclerView, new WrapperUtils.SpanSizeCallback() {
                @Override
                public int getSpanSize(GridLayoutManager layoutManager,
                                       GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        return layoutManager.getSpanCount();
                    } else if (mFootViews.get(viewType) != null) {
                        return layoutManager.getSpanCount();
                    }
                    if (oldLookup != null) {
                        return oldLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
        } else {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override
    public CBindingDiffAdapter<T> addHeaderView(View headerView) {
        final ViewGroup.LayoutParams lp = headerView.getLayoutParams();
        if (null == lp) {
            Log.e(TAG, "Warning!!! The view have no LayoutParams, you may not set parent when inflated...");
        }
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, headerView);
        return this;
    }

    @Override
    public CBindingDiffAdapter<T> removeHeaderView(View headerView) {
        int viewIndex = mHeaderViews.indexOfValue(headerView);
        mHeaderViews.removeAt(viewIndex);
        return this;
    }

    @Override
    public CBindingDiffAdapter<T> addFooterView(View footerView) {
        final ViewGroup.LayoutParams lp = footerView.getLayoutParams();
        if (null == lp) {
            Log.e(TAG, "Warning!!! The view may have no LayoutParams, you may not set parent when inflated ...");
        }
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, footerView);
        return this;
    }

    @Override
    public CBindingDiffAdapter<T> removeFooterView(View footerView) {
        int viewIndex = mFootViews.indexOfValue(footerView);
        mFootViews.removeAt(viewIndex);
        return this;
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
        return super.getItemViewType(position);
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
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void onViewAttachedToWindow(BindingViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
            return;
        }
        // animation support
        if (animationEnabled) {
            if (!mFirstOnlyEnable || position > mLastPosition) {
                // set animation
                BaseAnimation animation = mSelectAnimation;
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    anim.setDuration(mDuration).start();
                    anim.setInterpolator(mInterpolator);
                }
                mLastPosition = position;
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(BindingViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        // clear animation
        if (animationEnabled) {
            holder.getConvertView().clearAnimation();
        }
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

    private int getFootersCount() {
        return mFootViews.size();
    }

    private int getRealItemCount() {
        return super.getItemCount();
    }

    /* <!---- multiItem support ----> */
    @Override
    public CBindingDiffAdapter<T> multiItemTypeSupport(MultiItemTypeSupport<T> multiItemTypeSupport) {
        this.mMultiItemTypeSupport = multiItemTypeSupport;
        return this;
    }

    /* <!---- animation support -----> */

    // animation enabled
    private boolean animationEnabled = false;
    private int mLastPosition = -1;

    /*<!-- interpolator --> */
    private Interpolator mInterpolator = new LinearInterpolator();
    /*<!-- animation duration --> */
    private int mDuration = 300;
    /*<!-- base animation --> */
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    /*<!-- load animation first time only --> */
    private boolean mFirstOnlyEnable = false;

    public CBindingDiffAdapter<T> animationSupport(boolean enabled) {
        this.animationEnabled = enabled;
        return this;
    }

    public CBindingDiffAdapter<T> animationSupport(boolean enabled, boolean firstOnlyEnable
            , Interpolator interpolator, int duration, BaseAnimation selectAnimation) {
        this.animationEnabled = enabled;
        if (enabled) {
            this.mFirstOnlyEnable = firstOnlyEnable;
            this.mInterpolator = (null == interpolator ? new LinearInterpolator() : interpolator);
            this.mDuration = (duration == -1 ? 300 : duration);
            this.mSelectAnimation = (null == selectAnimation ? new AlphaInAnimation() : selectAnimation);
        }
        return this;
    }

    public void enabledAnimation(boolean enabled) {
        this.animationEnabled = enabled;
    }

    public void enabledFirstOnlyAnim(boolean enabled) {
        this.mFirstOnlyEnable = enabled;
    }

    public void setSelectAnimation(BaseAnimation selectAnimation) {
        this.mSelectAnimation = selectAnimation;
    }

    public boolean isAnimationEnabled() {
        return this.animationEnabled;
    }

}
