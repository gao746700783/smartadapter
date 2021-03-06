package com.smart.pullrefresh.loadinglayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.library.R;
import com.smart.pullrefresh.library.LoadingLayoutBase;
import com.smart.pullrefresh.library.PullToRefreshBase;

/**
 * Created by zwenkai on 2015/12/19.
 */
public class JingDongHeaderLayout extends LoadingLayoutBase {

    static final String LOG_TAG = "PullToRefresh-JingDongHeaderLayout";

    private FrameLayout mInnerLayout;

    private final TextView mHeaderText;
    private final TextView mSubHeaderText;

    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;

    private ImageView mGoodsImage;
    private ImageView mPersonImage;
    private AnimationDrawable animP;

    public JingDongHeaderLayout(Context context) {
        this(context, PullToRefreshBase.Mode.PULL_FROM_START);
    }

    public JingDongHeaderLayout(Context context, PullToRefreshBase.Mode mode) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header_vertical_jingdong, this);

        mInnerLayout = (FrameLayout) findViewById(R.id.fl_inner);
        mHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        mSubHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        mGoodsImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_goods);
        mPersonImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_people);

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInnerLayout.getLayoutParams();
        lp.gravity = mode == PullToRefreshBase.Mode.PULL_FROM_END ? Gravity.TOP : Gravity.BOTTOM;

        // Load in labels
        mPullLabel = "??????????????????";
        mRefreshingLabel = "???????????????";
        mReleaseLabel = "???????????????";

        reset();
    }

    @Override
    public final int getContentSize() {
        return mInnerLayout.getHeight();
    }

    @Override
    public final void pullToRefresh() {
        mSubHeaderText.setText(mPullLabel);
    }

    @Override
    public final void onPull(float scaleOfLayout) {
        scaleOfLayout = scaleOfLayout > 1.0f ? 1.0f : scaleOfLayout;

        if (mGoodsImage.getVisibility() != View.VISIBLE) {
            mGoodsImage.setVisibility(View.VISIBLE);
        }

        //???????????????
        ObjectAnimator animAlphaP = ObjectAnimator.ofFloat(mPersonImage, "alpha", -1, 1).setDuration(300);
        animAlphaP.setCurrentPlayTime((long) (scaleOfLayout * 300));
        ObjectAnimator animAlphaG = ObjectAnimator.ofFloat(mGoodsImage, "alpha", -1, 1).setDuration(300);
        animAlphaG.setCurrentPlayTime((long) (scaleOfLayout * 300));

        //????????????
        //    ViewHelper.setPivotX(mPersonImage, 0);  // ???????????????
        //    ViewHelper.setPivotY(mPersonImage, 0);

        mPersonImage.setPivotX(0);
        mPersonImage.setPivotY(0);

        ObjectAnimator animPX = ObjectAnimator.ofFloat(mPersonImage, "scaleX", 0, 1).setDuration(300);
        animPX.setCurrentPlayTime((long) (scaleOfLayout * 300));
        ObjectAnimator animPY = ObjectAnimator.ofFloat(mPersonImage, "scaleY", 0, 1).setDuration(300);
        animPY.setCurrentPlayTime((long) (scaleOfLayout * 300));

        //ViewHelper.setPivotX(mGoodsImage, mGoodsImage.getMeasuredWidth());
        mGoodsImage.setPivotX(mGoodsImage.getMeasuredWidth());

        ObjectAnimator animGX = ObjectAnimator.ofFloat(mGoodsImage, "scaleX", 0, 1).setDuration(300);
        animGX.setCurrentPlayTime((long) (scaleOfLayout * 300));
        ObjectAnimator animGY = ObjectAnimator.ofFloat(mGoodsImage, "scaleY", 0, 1).setDuration(300);
        animGY.setCurrentPlayTime((long) (scaleOfLayout * 300));
    }

    @Override
    public final void refreshing() {
        mSubHeaderText.setText(mRefreshingLabel);

        if (animP == null) {
            mPersonImage.setImageResource(R.drawable.pull_refresh_anim_jingdong);
            animP = (AnimationDrawable) mPersonImage.getDrawable();
        }
        animP.start();
        if (mGoodsImage.getVisibility() == View.VISIBLE) {
            mGoodsImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public final void releaseToRefresh() {
        mSubHeaderText.setText(mReleaseLabel);
    }

    @Override
    public final void reset() {
        if (animP != null) {
            animP.stop();
            animP = null;
        }
        mPersonImage.setImageResource(R.drawable.app_refresh_people_0);
        if (mGoodsImage.getVisibility() == View.VISIBLE) {
            mGoodsImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
        mSubHeaderText.setText(label);
    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {
        mPullLabel = pullLabel;
    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        mRefreshingLabel = refreshingLabel;
    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {
        mReleaseLabel = releaseLabel;
    }

    @Override
    public void setTextTypeface(Typeface tf) {
        mHeaderText.setTypeface(tf);
    }
}