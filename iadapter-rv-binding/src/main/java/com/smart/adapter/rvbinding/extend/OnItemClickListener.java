package com.smart.adapter.rvbinding.extend;

import android.view.View;
import android.view.ViewGroup;

/**
 * Description: OnItemClickListener
 * <p>
 * User: qiangzhang
 * Date: 2017/6/23 上午11:22
 */
public interface OnItemClickListener<T> {

    /*-- item click --*/
    void onItemClick(ViewGroup parent, View view, T t, int position);

    /*-- item long click --*/
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}