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
import com.smart.adapter.recyclerview.headerfooter.IHeaderFooterAdapter;
import com.smart.swiperefresh.view.decoration.DividerGridItemDecoration;
import com.smart.swiperefresh.view.decoration.DividerItemDecoration;
import com.smart.swiperefresh.view.empty.EmptyViewLayout;
import com.smart.swiperefresh.view.empty.IEmptyView;
import com.smart.swiperefresh.view.footer.FooterViewLayout;
import com.smart.swiperefresh.view.footer.IFooterView;
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

    private RecyclerView.LayoutManager mLayoutManager;

    private IHeaderFooterAdapter mAdapter;

    private EndlessRecyclerOnScrollListener mRvScrollListener;
    // private RecyclerView.OnScrollListener mRvScrollListener;
    private boolean hasMoreData = true;

    private IRefreshListener mRefreshListener;
    private ILoadMoreListener mLoadMoreListener;
    private List<RecyclerView.ItemDecoration> mItemDecorations = new ArrayList<>();

    //View mGoTopView;

    // 空布局
    //View mEmptyView;
    IEmptyView iEmptyView;
    // View mFooterView;
    IFooterView iFooterView;

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
        // swipe refresh layout
        this.mSwipeRefreshLayout = new SwipeRefreshLayout(mContext);
        this.mSwipeRefreshLayout.setId(R.id.swipe_refresh_rv);
        //mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtils.dp2px(mContext, 10));
        this.mSwipeRefreshLayout.setColorSchemeColors(0xffff2500);
        this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
        addView(mSwipeRefreshLayout, mpLayoutParams);

        // footer view
        this.iFooterView = FooterViewLayout.newBuilder(mContext);

        // recyclerView
        this.mRecyclerView = new EmptyRecyclerView(mContext);
        this.setupRecyclerView();
        this.mSwipeRefreshLayout.addView(mRecyclerView, mpLayoutParams);

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
            public void onLoadMore(int currentPage, int lastViPosition) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore();

                    // footer view visible when load more
                    iFooterView.footerLoading();
                }
            }
        };
        mRecyclerView.addOnScrollListener(mRvScrollListener);

        iEmptyView = EmptyViewLayout.newBuilder(mContext).emptyText("没有记录");
        // set empty view here
        if (mRecyclerView instanceof EmptyRecyclerView && iEmptyView != null) {
            View mEmptyView = iEmptyView.getView();
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

        // 分割线
        if (layoutManager instanceof GridLayoutManager) {
            DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(mContext);
            mItemDecorations.add(itemDecoration);
            mRecyclerView.addItemDecoration(itemDecoration);
        } else if (layoutManager instanceof LinearLayoutManager) {
            DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                    DividerItemDecoration.VERTICAL);
            mItemDecorations.add(itemDecoration);
            mRecyclerView.addItemDecoration(itemDecoration);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(mContext);
            mItemDecorations.add(itemDecoration);
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        this.mLayoutManager = layoutManager;
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);

        // layoutManager 发生了改变 需要重设 OnScrollListener
        this.mRvScrollListener.resetLayoutManager(this.mLayoutManager);
        //        this.mRecyclerView.removeOnScrollListener(mRvScrollListener);
        //        // add scrollListener
        //        this.mRvScrollListener = new EndlessRecyclerOnScrollListener(this.mLayoutManager) {
        //            @Override
        //            public void onLoadMore(int currentPage) {
        //                if (mLoadMoreListener != null) {
        //                    mLoadMoreListener.onLoadMore();
        //                }
        //            }
        //        };
        //        this.mRecyclerView.addOnScrollListener(mRvScrollListener);

        return this;
    }

    private void clearItemDecorations() {
        if (mItemDecorations != null) {
            for (RecyclerView.ItemDecoration item : mItemDecorations) {
                this.mRecyclerView.removeItemDecoration(item);
            }

            mItemDecorations.clear();
        }
    }


    // <!-- public methods start -->

    public SwipeRefreshRecyclerView adapter(IHeaderFooterAdapter adapter) {
        this.mAdapter = adapter;
        this.mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);

        // add footer view by adapter
        View footerView = iFooterView.getFooterView();
        mAdapter.addFooterView(footerView);

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

    public SwipeRefreshRecyclerView emptyView(IEmptyView emptyView) {
        if (null != iEmptyView) {
            removeView(iEmptyView.getView());
        }
        iEmptyView = emptyView;

        RelativeLayout.LayoutParams emptyLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        emptyLP.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(iEmptyView.getView(), emptyLP);

        // set empty view here
        if (mRecyclerView instanceof EmptyRecyclerView && iEmptyView != null) {
            View mEmptyView = iEmptyView.getView();
            ((EmptyRecyclerView) mRecyclerView).setEmptyView(mEmptyView);
        }

        return this;
    }

    public SwipeRefreshRecyclerView footerView(IFooterView footerView) {
        iFooterView = footerView;

        // add footer view by adapter
        mAdapter.addFooterView(iFooterView.getFooterView());

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

        // load more complete
        iFooterView.footerLoadComplete();
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

        // footer load finish
        if (!this.hasMoreData) {
            this.iFooterView.footerLoadFinish();
        }
    }

    public void enableRefresh(boolean refreshable) {
        this.mSwipeRefreshLayout.setEnabled(refreshable);
    }

    public void refreshFailure() {
    }

    public void loadMoreFailure() {
        iFooterView.footerLoadFailure();
    }

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

    // <!-- public methods end -->

}
