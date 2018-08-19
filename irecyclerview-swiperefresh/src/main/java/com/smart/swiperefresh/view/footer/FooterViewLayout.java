package com.smart.swiperefresh.view.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.swiperefresh.R;

/**
 * Project name:  smartrecyclerview-master
 * Package name:  com.smart.swiperefresh.view.empty
 * Description:   ${todo}(用一句话描述该文件做什么)
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company
 *
 * @author che300
 * @version V1.0
 * Createdate:    2018-08-14-13:42
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * ${date}       che300         1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
public class FooterViewLayout extends RelativeLayout implements IFooterView {

    private final LayoutParams mpParams =
            new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private Context mContext;

    private ProgressBar mFooterPb;
    private TextView mFooterTv;

    public FooterViewLayout(Context context) {
        this(context, null);
    }

    public FooterViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        init(context);
    }

    public static FooterViewLayout newBuilder(Context context) {
        return new FooterViewLayout(context);
    }

    private void init(Context context) {
        this.mContext = context;

        View loadingLayout = View.inflate(context, R.layout.layout_loading, null);
        mFooterTv = (TextView) loadingLayout.findViewById(R.id.tv_footer);
        mFooterPb = (ProgressBar) loadingLayout.findViewById(R.id.pb_footer);
        addView(loadingLayout, mpParams);

    }

    @Override
    public IFooterView footerText(String text) {
        if (null != mFooterTv) {
            mFooterTv.setText(text);
        }
        return this;
    }

    @Override
    public IFooterView footerRetry(OnClickListener retryListener) {
        if (null != mFooterTv) {
            mFooterTv.setOnClickListener(retryListener);
        }
        return this;
    }

    @Override
    public IFooterView footerLoading() {
        if (null != mFooterTv) {
            mFooterTv.setText("拼命加载中");
        }

        if (null!= mFooterPb){
            mFooterPb.setVisibility(View.VISIBLE);
        }

        return this;
    }

    @Override
    public IFooterView footerLoadComplete() {
        if (null != mFooterTv) {
            mFooterTv.setText("加载完毕");
        }

        if (null!= mFooterPb){
            mFooterPb.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public IFooterView footerLoadFinish() {
        if (null != mFooterTv) {
            mFooterTv.setText("全部已加载");
        }

        if (null!= mFooterPb){
            mFooterPb.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public IFooterView footerLoadFailure() {
        if (null != mFooterTv) {
            mFooterTv.setText("点击重新加载");
        }

        if (null!= mFooterPb){
            mFooterPb.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public View getFooterView() {
        return this;
    }

    @Override
    public IFooterView footerView(View view) {
        removeAllViews();
        addView(view, mpParams);
        return this;
    }

}
