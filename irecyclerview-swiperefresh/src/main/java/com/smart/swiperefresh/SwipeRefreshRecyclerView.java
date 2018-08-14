package com.smart.swiperefresh;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.smart.adapter.recyclerview.IAdapter;
import com.smart.swiperefresh.view.decoration.DividerGridItemDecoration;
import com.smart.swiperefresh.view.decoration.DividerItemDecoration;
import com.smart.swiperefresh.view.recyclerview.EmptyRecyclerView;
import com.smart.swiperefresh.view.recyclerview.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Project name:  smartrecyclerview-master
 * Package name:  com.smart.swiperefresh
 * Description:   封装了 swipeRefreshLayout的RecyclerView，todo 嵌套滚动待完善
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company
 *
 * @author che300
 * @version V1.0
 * Createdate:    2018/5/29-16:41
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * ${date}       che300         1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
public class SwipeRefreshRecyclerView extends RelativeLayout implements NestedScrollingChild {

    private final ViewGroup.LayoutParams mpLayoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private final ViewGroup.LayoutParams wcLayoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    // 空布局
    View mEmptyView;
    private RecyclerView.LayoutManager mLayoutManager;

    private IAdapter mAdapter;

    private RecyclerView.OnScrollListener mRvScrollListener;
    private boolean hasMoreData = true;

    private IRefreshListener mRefreshListener;
    private ILoadMoreListener mLoadMoreListener;
    private List<RecyclerView.ItemDecoration> mItemDecorations = new ArrayList<>();
    //View mGoTopView;
    //View mFooterView;

    public SwipeRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        this.init();
    }

    private void init() {

        mSwipeRefreshLayout = new SwipeRefreshLayout(mContext);
        addView(mSwipeRefreshLayout, mpLayoutParams);
        //
        mRecyclerView = new EmptyRecyclerView(mContext);
        setupRecyclerView();
        //
        mSwipeRefreshLayout.addView(mRecyclerView, mpLayoutParams);
        //mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtils.dp2px(mContext, 10));
        mSwipeRefreshLayout.setColorSchemeColors(0xffff2500);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });

    }

    private void setupRecyclerView() {
        // layoutManager
        if (null == mLayoutManager) {
            mLayoutManager = new LinearLayoutManager(mContext);
            ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);

        // itemDecoration
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL);
        mItemDecorations.add(itemDecoration);
        mRecyclerView.addItemDecoration(itemDecoration);

        // add scrollListener
        mRvScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore();
                }
            }
        };
        mRecyclerView.addOnScrollListener(mRvScrollListener);

        // set empty view here
        if (mRecyclerView instanceof EmptyRecyclerView && mEmptyView != null) {
            ((EmptyRecyclerView) mRecyclerView).setEmptyView(mEmptyView);
        }

    }

    /**
     * orientation can be {@see OrientationHelper }
     *
     * @param layoutManager layoutManager
     *                      //     * @param spanCount     spanCount
     *                      //     * @param orientation   orientation
     * @return SwipeRefreshRecyclerView
     */
    public SwipeRefreshRecyclerView layoutManager(RecyclerView.LayoutManager layoutManager
            /*, int spanCount, int orientation*/) {

        // 清除分割线
        this.clearItemDecorations();

        if (layoutManager instanceof GridLayoutManager) {
//            ((GridLayoutManager) layoutManager).setSpanCount(spanCount);
//            ((GridLayoutManager) layoutManager).setOrientation(orientation);

            // 分割线
            DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(mContext);
            mItemDecorations.add(itemDecoration);
            mRecyclerView.addItemDecoration(itemDecoration);
        } else if (layoutManager instanceof LinearLayoutManager) {
//            ((LinearLayoutManager) layoutManager).setOrientation(orientation);

            // 分割线
            DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                    DividerItemDecoration.VERTICAL);
            mItemDecorations.add(itemDecoration);
            mRecyclerView.addItemDecoration(itemDecoration);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            ((StaggeredGridLayoutManager) layoutManager).setOrientation(orientation);
//            ((StaggeredGridLayoutManager) layoutManager).setSpanCount(spanCount);

            // 分割线
            DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(mContext);
            mItemDecorations.add(itemDecoration);
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        this.mLayoutManager = layoutManager;
        this.mRecyclerView.setLayoutManager(mLayoutManager);

        // layoutManager 发生了改变 需要重设 OnScrollListener
        this.mRecyclerView.removeOnScrollListener(mRvScrollListener);
        // add scrollListener
        this.mRvScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore();
                }
            }
        };
        this.mRecyclerView.addOnScrollListener(mRvScrollListener);

        return this;
    }

    public SwipeRefreshRecyclerView adapter(IAdapter adapter) {
        this.mAdapter = adapter;
        this.mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
        return this;
    }

    public SwipeRefreshRecyclerView refresh(IRefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
        return this;
    }

    public SwipeRefreshRecyclerView loadMore(ILoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
        return this;
    }

    public SwipeRefreshRecyclerView emptyView(View emptyView) {
        if (null != mEmptyView) {
            removeView(mEmptyView);
        }
        mEmptyView = emptyView;

        RelativeLayout.LayoutParams emptyLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        emptyLP.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mEmptyView, emptyLP);

        // set empty view here
        if (mRecyclerView instanceof EmptyRecyclerView && mEmptyView != null) {
            ((EmptyRecyclerView) mRecyclerView).setEmptyView(mEmptyView);
        }

        return this;
    }

    public void refreshComplete(List list) {
        refreshComplete(list, true);
    }

    public void refreshComplete(List list, boolean hasMore) {
        showRefreshing(false);
        this.mAdapter.setDataList(list);
        // check if has more data
        enableLoadMore(hasMore);
    }

    public void loadMoreComplete(List list) {
        loadMoreComplete(list, true);
    }

    public void loadMoreComplete(List list, boolean hasMore) {
        this.mAdapter.appendDataList(list);
        // check if has more data
        enableLoadMore(hasMore);
    }

    public void enableLoadMore(boolean hasMore) {
        if (hasMoreData && hasMore) {
            return;
        }

        if (!hasMoreData && !hasMore) {
            return;
        }

        this.hasMoreData = hasMore;

        // check if has more data
        if (null != mRvScrollListener) {
            this.mRecyclerView.removeOnScrollListener(this.mRvScrollListener);
            if (this.hasMoreData) {
                this.mRecyclerView.addOnScrollListener(this.mRvScrollListener);
            }
        }
    }

    public void enableRefresh(boolean refreshable) {
        this.mSwipeRefreshLayout.setEnabled(refreshable);
    }

    //public void refreshFailure(){}
    //public void loadMoreFailure(){}

    /**
     * 手动调用刷新
     */
    public void doRefresh() {
        showRefreshing(true);
        if (mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }
    }

    /**
     * 只显示刷新效果
     */
    private void showRefreshing(boolean isRefreshing) {
        //mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtils.dp2px(mContext, 10));
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public IAdapter getAdapter() {
        return this.mAdapter;
    }

    public List getListData() {
        return this.mAdapter.getDataList();
    }

    private void clearItemDecorations() {
        if (mItemDecorations != null) {
            for (RecyclerView.ItemDecoration item : mItemDecorations) {
                this.mRecyclerView.removeItemDecoration(item);
            }

            mItemDecorations.clear();
        }
    }

}
