package com.smart.recycler;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.LinearLayout;

import com.smart.adapter.recyclerview.IConverter;
import com.smart.adapter.recyclerview.IHolder;
import com.smart.view.decoration.DividerGridItemDecoration;
import com.smart.adapter.recyclerview.dragndrop.adapter.DragDropRecyclerAdapter;
import com.smart.adapter.recyclerview.dragndrop.helper.SimpleItemTouchHelperCallback;
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

        mAdapter = (DragDropRecyclerAdapter<String>) new DragDropRecyclerAdapter<String>(this, R.layout.layout_list_item, dataList)
                .bindViewAndData(new IConverter<String>() {
                    @Override
                    public void convert(IHolder holder, String item, int position) {
                        holder.setText(R.id.tv_data, item);
                    }
                });

        // use a linear layout manager
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRvList.setLayoutManager(mGridLayoutManager);

        // 动画
        mRvList.setItemAnimator(new DefaultItemAnimator());

        // 分割线
        DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(this);
        mRvList.addItemDecoration(itemDecoration);

        mRvList.setAdapter(mAdapter);
        mRvList.setEmptyView(mEmptyView);

        // SimpleItemTouchHelperCallback
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(mAdapter);
        callback.setLongPressDragEnabled(true);
        callback.setItemViewSwipeEnabled(true);

        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRvList);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Drag-n-drop grid");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator();
            //actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

}
