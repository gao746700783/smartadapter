package com.smart.adapter.recyclerview;

import android.view.View;

/**
 * IAdapter
 *
 * @param <T>
 * @author che300
 */
public interface IHeaderFooterAdapter<T> extends IAdapter<T> {

    public IHeaderFooterAdapter addHeaderView(View headerView);

    public IHeaderFooterAdapter addFooterView(View footerView);

}
