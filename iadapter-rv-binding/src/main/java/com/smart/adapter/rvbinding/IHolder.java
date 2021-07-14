package com.smart.adapter.rvbinding;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * IHolder
 *
 * @author xiaohuigao
 */
public interface IHolder {

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId viewId
     * @param <T>    T
     * @return T
     */
    public <T extends View> T getView(int viewId);

    /**
     * 设置TextView的值
     *
     * @param viewId viewId
     * @param text   text
     * @return this
     */
    public IHolder setText(int viewId, String text);

    public IHolder setImageResource(int viewId, int resId);

    public IHolder setImageBitmap(int viewId, Bitmap bitmap);

    public IHolder setImageDrawable(int viewId, Drawable drawable);

    public IHolder setBackgroundColor(int viewId, int color);

    public IHolder setBackgroundRes(int viewId, int backgroundRes);

    public IHolder setTextColor(int viewId, int textColor);

    public IHolder setTextColorRes(int viewId, int textColorRes);

    @SuppressLint("NewApi")
    public IHolder setAlpha(int viewId, float value);

    public IHolder setVisible(int viewId, boolean visible);

    public IHolder linkify(int viewId);

    public IHolder setTypeface(Typeface typeface, int... viewIds);

    public IHolder setProgress(int viewId, int progress);

    public IHolder setProgress(int viewId, int progress, int max);

    public IHolder setMax(int viewId, int max);

    public IHolder setRating(int viewId, float rating);

    public IHolder setRating(int viewId, float rating, int max);

    public IHolder setTag(int viewId, Object tag);

    public IHolder setTag(int viewId, int key, Object tag);

    public IHolder setChecked(int viewId, boolean checked);

    /**
     * 关于事件的
     */
    public IHolder setOnClickListener(int viewId, View.OnClickListener listener);

    public IHolder setOnTouchListener(int viewId, View.OnTouchListener listener);

    public IHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener);

    public View getConvertView();

    public int getPosition();

    public <D extends ViewDataBinding> D getBinding();

}
