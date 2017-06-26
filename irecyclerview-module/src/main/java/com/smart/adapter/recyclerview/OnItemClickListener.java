package com.smart.adapter.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Description: OnItemClickListener
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 */
public interface OnItemClickListener<T> {

    /*-- item click --*/
    void onItemClick(ViewGroup parent, View view, T t, int position);

    /*-- item long click --*/
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}