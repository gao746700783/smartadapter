package com.smart.adapter.recyclerview.dragndrop;

import android.content.Context;

import com.smart.adapter.recyclerview.BaseAdapter;
import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.dragndrop.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Description: DragDropRecyclerAdapter
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/28 上午11:13 <br/>
 * @author che300
 */
public class DragDropRecyclerAdapter<T> extends BaseAdapter<T>
        implements ItemTouchHelperAdapter {

    public DragDropRecyclerAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void onItemDismiss(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDataList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDataList, i, i - 1);
            }
        }

        //Collections.swap(mDataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
}
