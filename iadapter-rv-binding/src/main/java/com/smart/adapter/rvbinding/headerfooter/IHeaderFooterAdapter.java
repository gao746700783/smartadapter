package com.smart.adapter.rvbinding.headerfooter;

import android.view.View;

import com.smart.adapter.rvbinding.IAdapter;

/**
 * IAdapter
 *
 * @param <T>
 * @author che300
 */
public interface IHeaderFooterAdapter<T> extends IAdapter<T> {

    public IHeaderFooterAdapter addHeaderView(View headerView);

    public IHeaderFooterAdapter removeHeaderView(View headerView);

    public IHeaderFooterAdapter addFooterView(View footerView);

    public IHeaderFooterAdapter removeFooterView(View footerView);

}
