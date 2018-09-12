package com.smart.adapter.recyclerview.headerfooter;

import android.view.View;

import com.smart.adapter.recyclerview.IAdapter;

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
