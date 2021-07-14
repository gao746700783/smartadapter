package com.smart.adapter.rvbinding.extend;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

public class OnDataSetChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableArrayList<T>> {

    private RecyclerView.Adapter rvAdapter;

    public OnDataSetChangedCallback(RecyclerView.Adapter rvAdapter) {
        this.rvAdapter = rvAdapter;
    }

    @Override
    public void onChanged(ObservableArrayList<T> newItems) {
        this.rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(ObservableArrayList<T> newItems, int positionStart, int itemCount) {
        this.rvAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(ObservableArrayList<T> newItems, int positionStart, int itemCount) {
        this.rvAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(ObservableArrayList<T> newItems, int fromPosition, int toPosition, int itemCount) {
        this.rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeRemoved(ObservableArrayList<T> newItems, int positionStart, int itemCount) {
//        this.rvAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        this.rvAdapter.notifyDataSetChanged();
    }
}
