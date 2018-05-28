package com.smart.recycler;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.LinearLayout;

import com.smart.adapter.recyclerview.IConverter;
import com.smart.adapter.recyclerview.IHolder;
import com.smart.view.decoration.DividerItemDecoration;
import com.smart.adapter.recyclerview.dragndrop.adapter.DragDropRecyclerAdapter;
import com.smart.adapter.recyclerview.dragndrop.helper.SimpleItemTouchHelperCallback;
import com.smart.view.recyclerview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DragnDropActivity extends AppCompatActivity {

    private String[] mDatas = {
            "Adapter: A subclass of RecyclerView.Adapter responsible for providing views that represent items in a data set.",
            "Position: The position of a data item within an Adapter.",
            "Index: The index of an attached child view as used in a call to getChildAt(int). Contrast with Position.",
            "Binding: The process of preparing a child view to display data corresponding to a position within the adapter.",
            "Recycle (view): A view previously used to display data for a specific adapter position may be placed in a cache for later reuse to display the same type of data again later. This can drastically improve performance by skipping initial layout inflation or construction",
            "Scrap (view): A child view that has entered into a temporarily detached state during layout. Scrap views may be reused without becoming fully detached from the parent RecyclerView, either unmodified if no rebinding is required or modified by the adapter if the view was considered dirty.",
            "Dirty (view): A child view that must be rebound by the adapter before being displayed.",
            "hehe",
            "495948",
            "89757",
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

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        mRvList.addItemDecoration(itemDecoration);

        dataList.addAll(Arrays.asList(mDatas));

        mAdapter = (DragDropRecyclerAdapter<String>) new DragDropRecyclerAdapter<String>(this, R.layout.layout_list_item, dataList)
                .bindViewAndData(new IConverter<String>() {
                    @Override
                    public void convert(IHolder holder, String item, int position) {
                        holder.setText(R.id.tv_data, item);
                    }
                });
        mRvList.setAdapter(mAdapter);

        mRvList.setEmptyView(mEmptyView);

        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(mAdapter);
        callback.setLongPressDragEnabled(true);
        callback.setItemViewSwipeEnabled(true);

        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRvList);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Drag-n-drop");
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
