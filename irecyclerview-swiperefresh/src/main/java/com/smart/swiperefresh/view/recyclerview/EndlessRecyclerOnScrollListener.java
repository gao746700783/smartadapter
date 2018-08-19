package com.smart.swiperefresh.view.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by hcc on 16/8/7 21:18
 * 100332338@qq.com
 * <p>
 * 自定义RecyclerView上拉加载处理
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    private int previousTotal = 0;
    private boolean loading = true;
    private int currentPage = 1;

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerOnScrollListener(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager ||
                layoutManager instanceof GridLayoutManager ||
                layoutManager instanceof LinearLayoutManager) {
            this.mLayoutManager = layoutManager;
        } else {
            throw new RuntimeException(
                    "Unsupported LayoutManager used. " +
                            "Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }
    }

    public void resetLayoutManager(RecyclerView.LayoutManager layoutManagerr){
        this.mLayoutManager = layoutManagerr;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        // If a layout manager has already been set, get current scroll position.
        if (mLayoutManager != null) {
            if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                if (lastPositions == null) {
                    lastPositions = new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()];
                }
                ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
            } else {

                lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                //lastCompleteVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            }
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading
                && visibleItemCount > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisibleItemPosition >= totalItemCount - 1) {
            currentPage++;
            onLoadMore(currentPage,lastVisibleItemPosition);
            loading = true;
        }

    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions lastPositions
     * @return max vaule
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public abstract void onLoadMore(int currentPage,int lastViPosition);

}