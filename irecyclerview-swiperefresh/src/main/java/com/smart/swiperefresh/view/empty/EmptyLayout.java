package com.smart.swiperefresh.view.empty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
public class EmptyLayout extends RelativeLayout implements IEmptyView {

    private final RelativeLayout.LayoutParams wcParams = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private final RelativeLayout.LayoutParams mpParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    private Context mContext;
    private ImageView mEmptyIv;
    private TextView mEmptyTv;
    private Button mEmptyBtn;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        init(context);
    }

    public static EmptyLayout newBuilder(Context context) {
        return new EmptyLayout(context);
    }

    private void init(Context context) {
        this.mContext = context;

        View mEmptyView = View.inflate(context, R.layout.layout_empty, null);
        mEmptyTv = (TextView) mEmptyView.findViewById(R.id.tv_empty_text);
        mEmptyIv = (ImageView) mEmptyView.findViewById(R.id.iv_empty_img);
        mEmptyBtn = (Button) mEmptyView.findViewById(R.id.btn_empty);
        addView(mEmptyView, mpParams);

    }

    @Override
    public EmptyLayout emptyView(int viewResId) {
        removeAllViews();

        View view = View.inflate(mContext, viewResId, null);
        addView(view, mpParams);
        return this;
    }

    @Override
    public EmptyLayout emptyView(View view) {
        removeAllViews();
        addView(view, mpParams);
        return this;
    }

    @Override
    public EmptyLayout emptyImage(int imageResId) {
        if (null != mEmptyIv) {
            mEmptyIv.setImageResource(imageResId);
        }
        return this;
    }

    @Override
    public EmptyLayout emptyText(String text) {
        if (null != mEmptyTv) {
            mEmptyTv.setText(text);
        }
        return this;
    }

    @Override
    public EmptyLayout emptyClick(OnClickListener clickListener) {
        if (null != mEmptyBtn) {
            mEmptyBtn.setOnClickListener(clickListener);
        }
        return this;
    }

    public TextView getEmptyTv() {
        return this.mEmptyTv;
    }

    public ImageView getEmptyIv() {
        return this.mEmptyIv;
    }

    public Button getEmptyBtn() {
        return this.mEmptyBtn;
    }

}
