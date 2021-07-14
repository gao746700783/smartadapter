package com.smart.adapter.rvbinding.extend;

import android.view.View;

/**
 * Description: OnItemClickListener
 * <p>
 * User: qiangzhang
 * Date: 2017/6/23 上午11:22
 *
 * @author xhgao
 */
public interface OnItemClickListener<T> {

    /**
     * item click
     *
     * @param view     view
     * @param t        t
     * @param position pos
     */
    void onItemClick(View view, T t, int position);

    /**
     * item long click
     *
     * @param view     view
     * @param t        t
     * @param position pos
     */
    boolean onItemLongClick(View view, T t, int position);
}