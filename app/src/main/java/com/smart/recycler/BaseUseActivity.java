package com.smart.recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.ViewHolder;
import com.smart.pullrefresh.library.PullToRefreshBase;
import com.smart.pullrefresh.library.PullToRefreshBase.Mode;
import com.smart.pullrefresh.library.extras.SoundPullEventListener;
import com.smart.view.decoration.DividerItemDecoration;
import com.smart.view.loadinglayout.JingDongHeaderLayout;
import com.smart.view.recyclerview.EmptyRecyclerView;
import com.smart.view.recyclerview.LazyRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseUseActivity extends AppCompatActivity {

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

    LazyRecyclerView mRvList;
    CommonAdapter mAdapter;

    LinearLayout mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_use);


        mEmptyView = (LinearLayout) findViewById(R.id.linear_empty);

        dataList.addAll(Arrays.asList(mDatas));

        mRvList = (LazyRecyclerView) findViewById(R.id.rv_base_use);

        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<RecyclerView> soundListener = new SoundPullEventListener<>(this);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        mRvList.setOnPullEventListener(soundListener);

        mRvList.setHeaderLayout(new JingDongHeaderLayout(this));

        mAdapter = new CommonAdapter<String>(this, R.layout.layout_list_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, String o) {
                holder.setText(R.id.tv_data, o);
            }
        };

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.getRefreshableView().setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        mRvList.getRefreshableView().addItemDecoration(itemDecoration);

        mRvList.getRefreshableView().setAdapter(mAdapter);
        if (mRvList.getRefreshableView() instanceof EmptyRecyclerView) {
            ((EmptyRecyclerView) mRvList.getRefreshableView()).setEmptyView(mEmptyView);
        }

        mRvList.setMode(Mode.BOTH);
        // Set a listener to be invoked when the list should be refreshed.
        mRvList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Toast.makeText(BaseUseActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
                //new GetDataTask().execute();

                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                dataList.add("added a new item");
                //mAdapter.notifyItemInserted(dataList.size() - 1);
                mAdapter.notifyDataSetChanged();

                // Call onRefreshComplete when the list has been refreshed.
                mRvList.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                Toast.makeText(BaseUseActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
                //new GetDataTask().execute();

                dataList.add("added a new item");
                //mAdapter.notifyItemInserted(dataList.size() - 1);
                mAdapter.notifyDataSetChanged();

                // Call onRefreshComplete when the list has been refreshed.
                mRvList.onRefreshComplete();
            }
        });


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
            mAdapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.action_add) {

            dataList.add("added a new item");

            //mAdapter.notifyItemInserted(dataList.size() - 1);
            mAdapter.notifyDataSetChanged();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
