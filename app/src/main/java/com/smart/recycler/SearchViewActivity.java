package com.smart.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.IConverter;
import com.smart.adapter.recyclerview.IHolder;
import com.smart.adapter.recyclerview.ViewHolder;
import com.smart.view.decoration.DividerItemDecoration;
import com.smart.view.recyclerview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity {

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
    SearchAdapter mAdapter;

    LinearLayout mEmptyView;
    private MaterialSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_search);


        mEmptyView = (LinearLayout) findViewById(R.id.linear_empty);

        dataList.addAll(Arrays.asList(mDatas));

        mRvList = (EmptyRecyclerView) findViewById(R.id.rv_base_use);

        mAdapter = (SearchAdapter) new SearchAdapter<String>(this, R.layout.layout_list_item, dataList)
                .bindViewAndData(new IConverter<String>() {
                    @Override
                    public void convert(IHolder holder, String item, int position) {
                        holder.setText(R.id.tv_data, item);
                    }
                });

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        mRvList.addItemDecoration(itemDecoration);

        mRvList.setAdapter(mAdapter);

        mRvList.setEmptyView(mEmptyView);

        initToolbar();

        initSearchView();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SearchView");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator();
            //actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    private void initSearchView() {
        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);

        //mSearchView.setAnimation(new AlphaAnimation(0,1f));
        //mSearchView.setAnimationDuration(1000);
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.shape_cursor);
        mSearchView.setEllipsize(true);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter text
                mAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        //mSearchItemIcon = searchItem.getIcon();
        mSearchView.setMenuItem(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    class SearchAdapter<T> extends CommonAdapter<T> implements Filterable {

        private List<String> filteredList;

        private SearchFilter userFilter;

        public SearchAdapter(Context context, int layoutId, List datas) {
            super(context, layoutId, datas);

            this.filteredList = new ArrayList<>();
        }

        @Override
        public Filter getFilter() {
            if (userFilter == null)
                userFilter = new SearchFilter(this, dataList);
            return userFilter;
        }

    }

    private class SearchFilter extends Filter {

        private final SearchAdapter adapter;

        private final List<String> originalList;

        private final List<String> filteredList;

        private SearchFilter(SearchAdapter adapter, List<String> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (TextUtils.isEmpty(constraint) || constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final String user : originalList) {
                    if (user.contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredList.clear();
            adapter.filteredList.addAll((ArrayList<String>) results.values);

            adapter.setDataList(adapter.filteredList);
        }
    }

}
