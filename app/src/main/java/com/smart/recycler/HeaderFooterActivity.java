package com.smart.recycler;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.HeaderFooterWrapper;
import com.smart.adapter.recyclerview.IConverter;
import com.smart.adapter.recyclerview.IHolder;
import com.smart.adapter.recyclerview.ViewHolder;
import com.smart.view.decoration.DividerItemDecoration;
import com.smart.view.recyclerview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderFooterActivity extends AppCompatActivity {

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
    //HeaderFooterWrapper mAdapter;

    LinearLayout mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_search);


        mEmptyView = (LinearLayout) findViewById(R.id.linear_empty);

        dataList.addAll(Arrays.asList(mDatas));
        mRvList = (EmptyRecyclerView) findViewById(R.id.rv_base_use);


        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(mLayoutManager);
        // item decoration
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        mRvList.addItemDecoration(itemDecoration);

        // use head and footer
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(this, R.layout.layout_list_item, dataList)
                .bindViewAndData(new IConverter<String>() {
                    @Override
                    public void convert(IHolder holder, String item, int position) {
                        holder.setText(R.id.tv_data, item);
                    }
                });
        HeaderFooterWrapper<String> wrapper = new HeaderFooterWrapper<String>(commonAdapter);
        // add header
        View header = LayoutInflater.from(this).inflate(R.layout.layout_list_item_header, null);
        wrapper.addHeaderView(header);
        // add footer
        wrapper.addFootView(header);

        mRvList.setAdapter(wrapper);

        // empty view
        mRvList.setEmptyView(mEmptyView);

        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Header and Footer");
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
