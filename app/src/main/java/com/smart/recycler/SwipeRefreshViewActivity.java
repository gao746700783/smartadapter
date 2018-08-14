package com.smart.recycler;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.IConverter;
import com.smart.adapter.recyclerview.IHolder;
import com.smart.swiperefresh.ILoadMoreListener;
import com.smart.swiperefresh.IRefreshListener;
import com.smart.swiperefresh.SwipeRefreshRecyclerView;
import com.smart.swiperefresh.view.empty.EmptyLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwipeRefreshViewActivity extends AppCompatActivity {

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

    SwipeRefreshRecyclerView rv_base_swipe_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_swipe_refresh);

        initToolbar();

        dataList.addAll(Arrays.asList(mDatas));

        //    CommonAdapter<String> commonAdapter = new CommonAdapter<>(this, R.layout.layout_list_item, dataList)
        //            .bindViewAndData(new IConverter<String>() {
        //                @Override
        //                public void convert(IHolder holder, String item, int position) {
        //                    holder.setText(R.id.tv_data, item);
        //                }
        //            });
        rv_base_swipe_refresh = (SwipeRefreshRecyclerView) findViewById(R.id.rv_base_swipe_refresh);
        rv_base_swipe_refresh.adapter(new CommonAdapter<>(this, R.layout.layout_list_item, dataList)
                .bindViewAndData(new IConverter<String>() {
                    @Override
                    public void convert(IHolder holder, String item, int position) {
                        holder.setText(R.id.tv_data, item);
                    }
                }))
                .refresh(new IRefreshListener() {
                    @Override
                    public void onRefresh() {
                        postRefresh();
                    }
                })
                .loadMore(new ILoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        postLoadMore();
                    }
                })
                .emptyView(EmptyLayout.newBuilder(this)
                        .emptyText("没有记录")
                        .emptyImage(R.drawable.app_refresh_goods_0)
                        .emptyClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SwipeRefreshViewActivity.this, "重新加载", Toast.LENGTH_SHORT).show();
                                rv_base_swipe_refresh.doRefresh();
                            }
                        })
                )
        ;

        // 手动调用一次刷新
        rv_base_swipe_refresh.doRefresh();

    }

    private void postLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                list.add("load more new item" + dataList.size());

                rv_base_swipe_refresh.loadMoreComplete(list);
            }
        }, 1000);
    }

    private void postRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataList.add(0, "refresh new item" + dataList.size());
                if (dataList.size() > 20) dataList.clear();
                rv_base_swipe_refresh.refreshComplete(dataList);
            }
        }, 1000);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SwipeRefreshView");
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
