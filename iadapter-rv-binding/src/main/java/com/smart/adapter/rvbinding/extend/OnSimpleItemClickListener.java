package com.smart.adapter.rvbinding.extend;

import android.view.View;

/**
 * Description: OnItemClickListener
 * <p>
 * User: qiangzhang
 * Date: 2017/6/23 上午11:22
 */
public class OnSimpleItemClickListener<T> implements OnItemClickListener<T> {

    @Override
    public void onItemClick(View view, T o, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, T o, int position) {
        return true;
    }
}