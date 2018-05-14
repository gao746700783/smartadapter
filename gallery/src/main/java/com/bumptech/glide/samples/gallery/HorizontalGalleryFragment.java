package com.bumptech.glide.samples.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.smart.adapter.recyclerview.IConverter;
import com.smart.adapter.recyclerview.IHolder;
import com.smart.view.decoration.DividerGridItemDecoration;
import com.smart.view.decoration.DividerItemDecoration;

import java.util.List;

/**
 * Displays media store data in a recycler view.
 */
public class HorizontalGalleryFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<MediaStoreData>> {

    private RecyclerView recyclerView;

    DividerGridItemDecoration itemDecoration_stagger;
    DividerItemDecoration itemDecoration_linear_grid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(R.id.loader_id_media_store_data, null, this);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView = (RecyclerView) result.findViewById(R.id.recycler_view);

        // 分割线
        itemDecoration_stagger = new DividerGridItemDecoration(this.getContext());
        itemDecoration_linear_grid = new DividerItemDecoration(this.getContext(),
                DividerItemDecoration.VERTICAL);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(itemDecoration_linear_grid);

        return result;
    }

    @Override
    public Loader<List<MediaStoreData>> onCreateLoader(int i, Bundle bundle) {
        return new MediaStoreDataLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<MediaStoreData>> loader,
                               List<MediaStoreData> mediaStoreDataList) {
        Log.d("Loader fragment", "onLoadFinished called!");

        RecyclerCommonAdapter<MediaStoreData> mAdapter = (RecyclerCommonAdapter<MediaStoreData>) new RecyclerCommonAdapter<MediaStoreData>(
                getActivity(), R.layout.layout_list_item_video, mediaStoreDataList)
                .bindViewAndData(new IConverter<MediaStoreData>() {
                    @Override
                    public void convert(IHolder holder, MediaStoreData data, int position) {
                        holder.setText(R.id.tv_video_title, data.title);
                        holder.setText(R.id.tv_video_artist, data.uri.toString());

                        //this.loadImages(data, (ImageView) holder.getView(R.id.iv_video_thumb));
                    }
                });

        RecyclerViewPreloader<MediaStoreData> preLoader =
                new RecyclerViewPreloader<>(mAdapter.getGlideRequests(), mAdapter, mAdapter, 3);
        recyclerView.addOnScrollListener(preLoader);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<MediaStoreData>> loader) {
        // Do nothing.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_pull_up_down, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // 获取当前第一个可见Item的position
        int scrollPosition = 0;
        // If a layout manager has already been set, get current scroll position.
        if (layoutManager != null) {
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                scrollPosition = ((StaggeredGridLayoutManager) layoutManager)
                        .findFirstCompletelyVisibleItemPositions(null)[0];
            } else {
                scrollPosition = ((LinearLayoutManager) layoutManager)
                        .findFirstCompletelyVisibleItemPosition();
            }
        }

        if (id == R.id.action_layout_linear) {

            if (layoutManager instanceof GridLayoutManager ||
                    !(layoutManager instanceof LinearLayoutManager)) {
                // use a linear layout manager
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLayoutManager);

                // 分割线处理
                recyclerView.removeItemDecoration(itemDecoration_stagger);
                recyclerView.addItemDecoration(itemDecoration_linear_grid);
            }
            // scroll to position
            recyclerView.scrollToPosition(scrollPosition);

            return true;
        } else if (id == R.id.action_layout_grid) {

            if (!(layoutManager instanceof GridLayoutManager)) {
                // use a grid layout manager
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(this.getContext(), 3);
                mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mGridLayoutManager);

                // 分割线处理
                recyclerView.addItemDecoration(itemDecoration_stagger);
                recyclerView.removeItemDecoration(itemDecoration_linear_grid);
            }
            // scroll to position
            recyclerView.scrollToPosition(scrollPosition);

            return true;
        }
        //        else if (id == R.id.action_layout_stagger) {
        //            if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
        //                // use a s layout manager
        //                StaggeredGridLayoutManager mStaggeredLayoutManager =
        //                        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //                recyclerView.setLayoutManager(mStaggeredLayoutManager);
        //
        //                // 分割线处理
        //                recyclerView.removeItemDecoration(itemDecoration_linear_grid);
        //                recyclerView.addItemDecoration(itemDecoration_stagger);
        //            }
        //
        //            // scroll to position
        //            //recyclerView.scrollToPosition(scrollPosition);
        //
        //            return true;
        //        }

        return super.onOptionsItemSelected(item);

    }
}
