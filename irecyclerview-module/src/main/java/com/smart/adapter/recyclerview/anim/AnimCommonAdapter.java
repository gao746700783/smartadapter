package com.smart.adapter.recyclerview.anim;

import android.animation.Animator;
import android.content.Context;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.ViewHolder;
import com.smart.adapter.recyclerview.anim.animation.AlphaInAnimation;
import com.smart.adapter.recyclerview.anim.animation.ScaleInAnimation;
import com.smart.adapter.recyclerview.anim.animation.SlideInBottomAnimation;
import com.smart.adapter.recyclerview.anim.animation.SlideInLeftAnimation;
import com.smart.adapter.recyclerview.anim.animation.SlideInRightAnimation;
import com.smart.adapter.recyclerview.anim.animation.base.BaseAnimation;

import java.util.List;

/**
 * Description: CommonAdapter
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 */
public class AnimCommonAdapter<T> extends CommonAdapter<T> {

    // animation enabled
    private boolean animationEnabled = true;
    private int mLastPosition = -1;

    //Animation
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int ALPHAIN = 0x00000001;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SCALEIN = 0x00000002;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_LEFT = 0x00000004;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_RIGHT = 0x00000005;

    /*<!-- interpolator --> */
    private Interpolator mInterpolator = new LinearInterpolator();
    /*<!-- animation duration --> */
    private int mDuration = 300;
    /*<!-- base animation --> */
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    /*<!-- load animation first time only --> */
    private boolean mFirstOnlyEnable = true;

    public AnimCommonAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    /**
     * Set the view animation type.
     *
     * @param animationType One of {@link #ALPHAIN}, {@link #SCALEIN}, {@link #SLIDEIN_BOTTOM},
     *                      {@link #SLIDEIN_LEFT}, {@link #SLIDEIN_RIGHT}.
     */
    public void openLoadAnimation(int animationType) {
        this.animationEnabled = true;
        switch (animationType) {
            case ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            default:
                break;
        }
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        if (animationEnabled) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                // set animation
                BaseAnimation animation = mSelectAnimation;
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    anim.setDuration(mDuration).start();
                    anim.setInterpolator(mInterpolator);
                }

                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        // clear animation
        holder.getConvertView().clearAnimation();
    }

    public void setAnimationEnabled(boolean enableAnimation) {
        this.animationEnabled = enableAnimation;
    }

    public boolean isAnimationEnabled() {
        return this.animationEnabled;
    }

    public void setFirstTimeAnimEnabled(boolean firstEnable) {
        this.mFirstOnlyEnable = firstEnable;
    }

    /**
     * Sets the duration of the animation.
     *
     * @param duration The length of the animation, in milliseconds.
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

}
