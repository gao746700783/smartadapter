package com.smart.recycler.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smart.adapter.recyclerview.headerfooter.HeaderFooterAdapter;
import com.smart.recycler.R;
import com.smart.swiperefresh.SwipeRefreshRecyclerView;
import com.smart.swiperefresh.view.empty.EmptyViewLayout;
import com.smart.view.decoration.DividerGridItemDecoration;
import com.smart.view.decoration.DividerItemDecoration;

import java.util.List;

/**
 * Displays media store data in a recycler view.
 */
public class HorizontalGalleryFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<MediaStoreData>> {

    private SwipeRefreshRecyclerView mGalleryRv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.layout_gallery_frag, container, false);
        mGalleryRv = (SwipeRefreshRecyclerView) result.findViewById(R.id.rv_base_gallery);

        mGalleryRv.adapter(new HeaderFooterAdapter<MediaStoreData>(this.getContext(), R.layout.layout_list_item_media)
                .bindViewAndData((holder, item, position) -> {
                    holder.setText(R.id.tv_video_title, item.title);
                    holder.setText(R.id.tv_video_artist, item.uri.toString());

                    Glide.with(getActivity())
                                .asDrawable()
                                .load(item.uri)
                                .into((ImageView) holder.getView(R.id.iv_video_thumb));
                }))
                .emptyView(EmptyViewLayout.newBuilder(this.getContext())
                                .emptyText("没有记录")
                                .emptyImage(R.drawable.app_refresh_goods_0)
                                .emptyClick(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                Toast.makeText(SwipeRefreshViewActivity.this, "重新加载", Toast.LENGTH_SHORT).show();
//                                rv_base_swipe_refresh.doRefresh();
                                    }
                                })
                );

        mGalleryRv.enableLoadMore(false);
        mGalleryRv.enableRefresh(false);

        getLoaderManager().initLoader(R.id.loader_id_media_store_data, null, this);

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
        mGalleryRv.getAdapter().setDataList(mediaStoreDataList);
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

        if (id == R.id.action_layout_linear) {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mGalleryRv.layoutManager(mLayoutManager);

            return true;
        } else if (id == R.id.action_layout_grid) {
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(this.getContext(), 3);
            mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            mGalleryRv.layoutManager(mGridLayoutManager);

            return true;
        } else if (id == R.id.action_layout_stagger) {
            // use a s layout manager
            StaggeredGridLayoutManager mStaggeredLayoutManager =
                    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mGalleryRv.layoutManager(mStaggeredLayoutManager);

            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
