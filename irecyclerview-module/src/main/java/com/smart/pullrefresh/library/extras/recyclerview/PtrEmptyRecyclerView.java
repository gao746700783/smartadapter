package com.smart.pullrefresh.library.extras.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import com.smart.pullrefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.smart.view.recyclerview.EmptyRecyclerView;

/**
 * Description: LazyRecyclerView
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/23 下午4:52 <br/>
 */
public class PtrEmptyRecyclerView extends PullToRefreshRecyclerView {

    public PtrEmptyRecyclerView(Context context) {
        super(context);
    }

    public PtrEmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrEmptyRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PtrEmptyRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    protected EmptyRecyclerView createRefreshableView(Context context,
                                                      AttributeSet attrs) {
        EmptyRecyclerView recyclerView = new EmptyRecyclerView(context, attrs);
        return recyclerView;
    }

}