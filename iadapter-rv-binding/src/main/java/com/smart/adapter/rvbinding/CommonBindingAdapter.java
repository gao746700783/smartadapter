package com.smart.adapter.rvbinding;

import android.content.Context;
import android.databinding.ViewDataBinding;

import java.util.List;

/**
 * Description: CommonAdapter extends BaseAdapter
 * todo
 * 扩展特性
 * 1. HeaderFooter
 * 2. multiItem support
 * 3. animation support
 * 4. drag n drop support
 * 5.
 *
 * <p>
 * User: gxh
 * Date: 2017/6/23 上午11:22
 *
 * @author gaoxiaohui
 */
public class CommonBindingAdapter<T, D extends ViewDataBinding> extends BaseBindingAdapter<T, D> {

    public CommonBindingAdapter(Context context) {
        super(context);
    }

    public CommonBindingAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public CommonBindingAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    public CommonBindingAdapter<T, D> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    public CommonBindingAdapter<T, D> list(List<T> datas) {
        this.mDataList = datas;
        return this;
    }

    public CommonBindingAdapter<T, D> bindViewAndData(IConverter<? super T> converter) {
        this.mIConverter = converter;
        return this;
    }

//    @Override
//    public BindingViewHolder<D> onCreateViewHolder(ViewGroup parent, int viewType) {
//        return super.onCreateViewHolder(parent, viewType);
//    }
//
//    @Override
//    public void onBindViewHolder(BindingViewHolder<D> holder, int position) {
//        super.onBindViewHolder(holder, position);
//    }

//    @Override
//    public void onViewDetachedFromWindow(BindingViewHolder<D> holder) {
//        super.onViewDetachedFromWindow(holder);
////        // clear animation
////        if (animationEnabled) {
////            holder.getConvertView().clearAnimation();
////        }
//    }

//    @Override
//    public void onViewAttachedToWindow(BindingViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
////        int position = holder.getLayoutPosition();
////        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
////            WrapperUtils.setFullSpan(holder);
////            return;
////        }
////
////        // animation support
////        if (animationEnabled) {
////            if (!mFirstOnlyEnable || position > mLastPosition) {
////                // set animation
////                BaseAnimation animation = mSelectAnimation;
////                for (Animator anim : animation.getAnimators(holder.itemView)) {
////                    anim.setDuration(mDuration).start();
////                    anim.setInterpolator(mInterpolator);
////                }
////
////                mLastPosition = position;
////            }
////        }
//    }

//    @Override
//    public T getItem(int position) {
////        // position < headerView 数目
////        if (position < getHeadersCount()) {
////            return null;
////        }
////
////        // reset position
////        position = position - getHeadersCount();
//        return (T) super.getItem(position);
//    }

//    @Override
//    public int getItemCount() {
////        return getHeadersCount() + getFootersCount() + getRealItemCount();
//        return super.getItemCount();
//    }

//    @Override
//    public int getItemViewType(int position) {
////        if (isHeaderViewPos(position)) {
////            return mHeaderViews.keyAt(position);
////        } else if (isFooterViewPos(position)) {
////            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
////        }
////
////        // reset position
////        position = position - getHeadersCount();
////        if (mMultiItemTypeSupport != null) {
////            return mMultiItemTypeSupport.getItemViewType(position, getItem(position));
////        }
//        return super.getItemViewType(position);
//    }

//    public int getRealItemCount() {
//        return mDataList.size();
//    }

//    private boolean isHeaderViewPos(int position) {
//        return position < getHeadersCount();
//    }
//
//    private boolean isFooterViewPos(int position) {
//        return position >= getHeadersCount() + getRealItemCount();
//    }
//
//    public int getHeadersCount() {
//        return mHeaderViews.size();
//    }
//
//    public int getFootersCount() {
//        return mFootViews.size();
//    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//        if (layoutManager instanceof GridLayoutManager) {
//            WrapperUtils.onAttachedToRecyclerView(/*this, */recyclerView,
//                    new WrapperUtils.SpanSizeCallback() {
//                        @Override
//                        public int getSpanSize(GridLayoutManager layoutManager,
//                                               GridLayoutManager.SpanSizeLookup oldLookup, int position) {
////                            int viewType = getItemViewType(position);
////                            if (mHeaderViews.get(viewType) != null) {
////                                return layoutManager.getSpanCount();
////                            } else if (mFootViews.get(viewType) != null) {
////                                return layoutManager.getSpanCount();
////                            }
//                            if (oldLookup != null)
//                                return oldLookup.getSpanSize(position);
//                            return 1;
//                        }
//                    });
//        } else {
//            super.onAttachedToRecyclerView(recyclerView);
//        }
//    }

//    @Override
//    public CommonBindingAdapter<T> addHeaderView(View headerView) {
//        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, headerView);
//        return this;
//    }
//
//    @Override
//    public CommonBindingAdapter<T> removeHeaderView(View headerView) {
//        int viewIndex = mHeaderViews.indexOfValue(headerView);
//        mHeaderViews.removeAt(viewIndex);
//        return this;
//    }
//
//    @Override
//    public CommonBindingAdapter<T> addFooterView(View footerView) {
//        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, footerView);
//        return this;
//    }
//
//    @Override
//    public CommonBindingAdapter<T> removeFooterView(View footerView) {
//        int viewIndex = mFootViews.indexOfValue(footerView);
//        mFootViews.removeAt(viewIndex);
//        return this;
//    }

//    /* <!---- multiItem support ----> */
//    private MultiItemTypeSupport<T> mMultiItemTypeSupport = null;
//
//    public CommonBindingAdapter<T> multiItemTypeSupport(MultiItemTypeSupport<T> multiItemTypeSupport) {
//        this.mMultiItemTypeSupport = multiItemTypeSupport;
//        return this;
//    }
//
//    /* <!---- animation support -----> */
//
//    // animation enabled
//    private boolean animationEnabled = false;
//    private int mLastPosition = -1;
//
//    /*<!-- interpolator --> */
//    private Interpolator mInterpolator = new LinearInterpolator();
//    /*<!-- animation duration --> */
//    private int mDuration = 300;
//    /*<!-- base animation --> */
//    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
//    /*<!-- load animation first time only --> */
//    private boolean mFirstOnlyEnable = false;
//
//    public CommonBindingAdapter<T> animationSupport(boolean enabled) {
//        this.animationEnabled = enabled;
//        return this;
//    }
//
//    public CommonBindingAdapter<T> animationSupport(boolean enabled, boolean firstOnlyEnable
//            , Interpolator interpolator, int duration, BaseAnimation selectAnimation) {
//        this.animationEnabled = enabled;
//        if (enabled) {
//            this.mFirstOnlyEnable = firstOnlyEnable;
//            this.mInterpolator = (null == interpolator ? new LinearInterpolator() : interpolator);
//            this.mDuration = (duration == -1 ? 300 : duration);
//            this.mSelectAnimation = (null == selectAnimation ? new AlphaInAnimation() : selectAnimation);
//        }
//        return this;
//    }
//
//    public void enabledAnimation(boolean enabled) {
//        this.animationEnabled = enabled;
//    }
//
//    public void enabledFirstOnlyAnim(boolean enabled) {
//        this.mFirstOnlyEnable = enabled;
//    }
//
//    public void setSelectAnimation(BaseAnimation selectAnimation) {
//        this.mSelectAnimation = selectAnimation;
//    }
//
//    public boolean isAnimationEnabled() {
//        return this.animationEnabled;
//    }
//
//    /* <!---- drag n drop support -----> */
//
//    // animation enabled
//    private boolean dragndropEnabled = false;
//    private SimpleItemTouchHelperCallback itemTouchHelperCallback;
//
//    public CommonBindingAdapter<T> dragndropSupport(boolean enabled, RecyclerView recyclerView) {
//        this.dragndropEnabled = enabled;
//        if (dragndropEnabled) {
//            itemTouchHelperCallback = new SimpleItemTouchHelperCallback(this);
//            itemTouchHelperCallback.setLongPressDragEnabled(true);
//            itemTouchHelperCallback.setItemViewSwipeEnabled(true);
//
//            ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
//            mItemTouchHelper.attachToRecyclerView(recyclerView);
//        }
//
//        return this;
//    }
//
//    public void enabledDragnDrop(boolean enabled) {
//        this.dragndropEnabled = enabled;
//        if (null == itemTouchHelperCallback) {
//            itemTouchHelperCallback = new SimpleItemTouchHelperCallback(this);
//        }
//        itemTouchHelperCallback.setLongPressDragEnabled(enabled);
//        itemTouchHelperCallback.setItemViewSwipeEnabled(enabled);
//
////        if (enabled){
////            ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
////            mItemTouchHelper.attachToRecyclerView(recyclerView);
////        }
//    }
//
//    @Override
//    public void onItemDismiss(int position) {
//        mDataList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    @Override
//    public void onItemMove(int fromPosition, int toPosition) {
//        if (fromPosition < toPosition) {
//            for (int i = fromPosition; i < toPosition; i++) {
//                Collections.swap(mDataList, i, i + 1);
//            }
//        } else {
//            for (int i = fromPosition; i > toPosition; i--) {
//                Collections.swap(mDataList, i, i - 1);
//            }
//        }
//
//        //Collections.swap(mDataList, fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
//    }

}
