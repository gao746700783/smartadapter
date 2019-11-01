package com.smart.adapter.recyclerview;

import java.util.List;

/**
 * IAdapter
 *
 * @param <A><T> A T
 * @author che300
 */
public interface IAdapter<A, T> {

    public A getAdapter();

    public List<? super T> getDataList();

    public void setDataList(List<T> list);

    public void appendDataList(List<T> list);

    public void notifyDataChanged();

}
