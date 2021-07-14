package com.smart.adapter.pager.base;

import android.databinding.ObservableArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * IAdapter
 *
 * @param <T> T
 * @author xiaohuigao
 */
public interface IPagerAdapter<T> {

    public void resetDataList(List<T> list);

    public void resetDataList(ArrayList<T> list);

    public void resetDataList(ObservableArrayList<T> newItems);

}
