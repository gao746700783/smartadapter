package com.smart.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import com.smart.pullrefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

/**
 * Description: LazyRecyclerView
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/23 下午4:52 <br/>
 */
public class LazyRecyclerView extends PullToRefreshRecyclerView {


    public LazyRecyclerView(Context context) {
        super(context);
    }

    public LazyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LazyRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public LazyRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    protected EmptyRecyclerView createRefreshableView(Context context,
                                                      AttributeSet attrs) {
        EmptyRecyclerView recyclerView = new EmptyRecyclerView(context, attrs);
        return recyclerView;
    }

}