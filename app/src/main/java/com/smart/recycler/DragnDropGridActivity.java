package com.smart.recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.LinearLayout;

import com.smart.adapter.recyclerview.ViewHolder;
import com.smart.view.decoration.DividerItemDecoration;
import com.smart.view.dragndrop.adapter.DragDropRecyclerAdapter;
import com.smart.view.dragndrop.helper.SimpleItemTouchHelperCallback;
import com.smart.view.recyclerview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DragnDropGridActivity extends AppCompatActivity {

    private String[] mDatas = {
            "hehe",
            "view",
            "child",
            "that",
            "index",
            "previously",
            "temporarily",
            "display",
            "495948",
            "subclass",
            "state",
            "495948",
            "Contrast",
            "Adapter",
            "Recycle",
            "495948",
            "495948",
            "Scrap",
            "66666"
    };
    private List<String> dataList = new ArrayList<>();

    EmptyRecyclerView mRvList;
    DragDropRecyclerAdapter<String> mAdapter;

    LinearLayout mEmptyView;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use);


        mEmptyView = (LinearLayout) findViewById(R.id.linear_empty);
        mRvList = (EmptyRecyclerView) findViewById(R.id.rv_base_use);

        dataList.addAll(Arrays.asList(mDatas));

        mAdapter = new DragDropRecyclerAdapter<String>(this, R.layout.layout_list_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, String o) {
                holder.setText(R.id.tv_data, o);
            }
        };

        // use a linear layout manager
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRvList.setLayoutManager(mGridLayoutManager);
        // divider item decoration
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        mRvList.addItemDecoration(itemDecoration);

        mRvList.setAdapter(mAdapter);
        mRvList.setEmptyView(mEmptyView);

        // SimpleItemTouchHelperCallback
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(mAdapter);
        callback.setLongPressDragEnabled(true);
        callback.setItemViewSwipeEnabled(true);

        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRvList);

    }

}
