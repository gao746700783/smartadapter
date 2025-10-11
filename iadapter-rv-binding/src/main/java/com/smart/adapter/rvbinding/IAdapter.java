package com.smart.adapter.rvbinding;

import androidx.databinding.ObservableArrayList;

import java.util.List;

/**
 * IAdapter
 *
 * @param <T> T
 * @author che300
 */
public interface IAdapter<T> {

//    public A getAdapter();
//
//    public List<? super T> getDataList();
//
//    public void setDataList(List<T> list);
//
//    public void appendDataList(List<T> list);
//
//    public void notifyDataChanged();

    public void resetDataList(List<T> list);

//    public void resetDataList(ArrayList<T> list);

    public void resetDataList(ObservableArrayList<T> newItems);

}
