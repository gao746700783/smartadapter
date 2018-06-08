package com.smart.swiperefresh;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.adapter.recyclerview.IAdapter;
import com.smart.swiperefresh.view.decoration.DividerItemDecoration;
import com.smart.swiperefresh.view.recyclerview.EmptyRecyclerView;
import com.smart.swiperefresh.view.recyclerview.EndlessRecyclerOnScrollListener;

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

    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private IAdapter mAdapter;

    private RecyclerView.OnScrollListener mRvScrollListener;
    private boolean hasMoreData = true;

    private IRefreshListener mRefreshListener;
    private ILoadMoreListener mLoadMoreListener;

    // TODO
    // 待完善 空布局（——包含失败布局等）
    View mEmptyView;

    ImageView mEmptyIv;
    TextView mEmptyTv;
    Button mEmptyBtn;

    View mGoTopView;


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

        ViewGroup.LayoutParams mpLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams wpLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mSwipeRefreshLayout = new SwipeRefreshLayout(mContext);
        addView(mSwipeRefreshLayout, mpLayoutParams);

        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_custom_empty, this, false);
        addView(mEmptyView, mpLayoutParams);
        setupEmptyView();

        mRecyclerView = new EmptyRecyclerView(mContext);
        setupRecyclerView();

        mSwipeRefreshLayout.addView(mRecyclerView, wpLayoutParams);
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

    private void setupEmptyView() {
        mEmptyIv = (ImageView) mEmptyView.findViewById(R.id.iv_empty);
        mEmptyTv = (TextView) mEmptyView.findViewById(R.id.tv_empty);
        mEmptyBtn = (Button) mEmptyView.findViewById(R.id.btn_empty);

        mEmptyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doRefresh();
            }
        });
    }

    private void setupRecyclerView() {
        // layoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // itemDecoration
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL);
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

    public SwipeRefreshRecyclerView adapter(IAdapter adapter) {
        this.mAdapter = adapter;
        if (mRecyclerView instanceof EmptyRecyclerView) {
            this.mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
        } else {
            this.mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
        }
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

        ViewGroup.LayoutParams mpLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mEmptyView, mpLayoutParams);

        // set empty view here
        if (mRecyclerView instanceof EmptyRecyclerView && mEmptyView != null) {
            ((EmptyRecyclerView) mRecyclerView).setEmptyView(mEmptyView);
        }

        return this;
    }

    public SwipeRefreshRecyclerView emptyImage(int imageResId) {
        if (null != mEmptyView && null != mEmptyIv) {
            mEmptyIv.setImageResource(imageResId);
        }
        return this;
    }

    public SwipeRefreshRecyclerView emptyText(String text) {
        if (null != mEmptyView && null != mEmptyTv) {
            mEmptyTv.setText(text);
        }
        return this;
    }

    public SwipeRefreshRecyclerView emptyClick(OnClickListener clickListener) {
        if (null != mEmptyView && null != mEmptyBtn) {
            mEmptyBtn.setOnClickListener(clickListener);
        }
        return this;
    }

    public void refreshComplete(List list) {
        refreshComplete(list, true);
    }

    public void refreshComplete(List list, boolean hasMore) {
        showRefreshing(false);
        this.mAdapter.setDataList(list);

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
        if (hasMoreData && hasMore){
            return;
        }

        if (!hasMoreData && !hasMore){
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

    public void enableRefresh(boolean refreshable){
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
    private void showRefreshing(boolean isRefresh) {
        //mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtils.dp2px(mContext, 10));
        mSwipeRefreshLayout.setRefreshing(isRefresh);
    }

}
