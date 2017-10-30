package com.bumptech.glide.samples.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.smart.adapter.recyclerview.ViewHolder;
import com.smart.view.decoration.DividerItemDecoration;

import java.util.List;

/**
 * Displays media store data in a recycler view.
 */
public class HorizontalGalleryFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<MediaStoreData>> {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(R.id.loader_id_media_store_data, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView = (RecyclerView) result.findViewById(R.id.recycler_view);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

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
        GlideRequests glideRequests = GlideApp.with(this);

        RecyclerCommonAdapter mAdapter = new RecyclerCommonAdapter<MediaStoreData>(getActivity(),
                R.layout.layout_list_item_video, mediaStoreDataList, glideRequests) {
            @Override
            protected void convert(final ViewHolder holder, MediaStoreData o) {
                holder.setText(R.id.tv_video_title, o.title);
                holder.setText(R.id.tv_video_artist, o.uri.toString());
                loadImages(o, (ImageView) holder.getView(R.id.iv_video_thumb));
            }
        };

        RecyclerViewPreloader<MediaStoreData> preLoader =
                new RecyclerViewPreloader<MediaStoreData>(glideRequests, mAdapter, mAdapter, 3);
        recyclerView.addOnScrollListener(preLoader);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<MediaStoreData>> loader) {
        // Do nothing.
    }
}
