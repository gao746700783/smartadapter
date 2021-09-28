package com.smart.recycler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.smart.adapter.rvbinding.advances.CBindingListAdapter;
import com.smart.adapter.rvbinding.IConverter;
import com.smart.adapter.rvbinding.IHolder;
import com.smart.view.decoration.DividerItemDecoration;
import com.smart.view.recyclerview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RvBindingActivity extends AppCompatActivity {

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
    CBindingListAdapter<String> mAdapter;

    LinearLayout mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_binding);

        mEmptyView = (LinearLayout) findViewById(R.id.linear_empty);

        mRvList = (EmptyRecyclerView) findViewById(R.id.rv_base_use);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                mLayoutManager.getOrientation());
        mRvList.addItemDecoration(itemDecoration);

        dataList.addAll(Arrays.asList(mDatas));
        mAdapter = new CBindingListAdapter<String>(this, new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(String s, String t1) {
                return TextUtils.equals(s, t1);
            }

            @Override
            public boolean areContentsTheSame(String s, String t1) {
                return TextUtils.equals(s, t1);
            }
        })
                .layout(R.layout.layout_list_item_rv)
                .list(dataList)
                .bindViewAndData(new IConverter<String>() {
                    @Override
                    public void convert(IHolder holder, String item, int position) {
                        @SuppressLint("DefaultLocale")
                        String s = String.format("%d %2s", position, item);
                        holder.setText(R.id.tv_data, s);
                    }

                    @Override
                    public int getVariableId(int viewType) {
                        return BR.testVM;
                    }
                });
        mRvList.setAdapter(mAdapter);

        mRvList.setEmptyView(mEmptyView);
        initToolbar();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Base use");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base_use, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_empty) {
            dataList.clear();
            List<String> dataListNew = new ArrayList<>(dataList);
            mAdapter.resetDataList(dataListNew);
            return true;
        } else if (id == R.id.action_add) {
            dataList.add("added a new item" + dataList.size());
            List<String> dataListNew = new ArrayList<>();
            dataListNew.addAll(dataList);
            // 使用ListAdapter或AsyncDiffUtil 实现的adapter 需用全新的list，否则不刷新
            mAdapter.resetDataList(dataListNew);
            mRvList.smoothScrollToPosition(dataListNew.size());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
