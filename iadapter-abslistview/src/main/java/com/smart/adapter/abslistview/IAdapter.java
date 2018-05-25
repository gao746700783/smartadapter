package com.smart.adapter.abslistview;

import java.util.List;

/**
 * IAdapter
 * @author che300
 * @param <T>
 */
public interface IAdapter<T> {

    public T getAdapter();

    public List getDataList();

    public void setDataList(List list);

    public void appendDataList(List list);

    public void notifyDataChanged();

}
