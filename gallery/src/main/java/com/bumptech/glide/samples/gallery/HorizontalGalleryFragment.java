package com.bumptech.glide.samples.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.signature.MediaStoreSignature;
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

//    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
//    layoutManager.setOrientation(RecyclerView.VERTICAL);
//    //LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
//    recyclerView.setLayoutManager(layoutManager);
//    recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        return result;
    }

    @Override
    public Loader<List<MediaStoreData>> onCreateLoader(int i, Bundle bundle) {
        return new MediaStoreDataLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<MediaStoreData>> loader,
                               List<MediaStoreData> mediaStoreData) {
        GlideRequests glideRequests = GlideApp.with(this);

//    RecyclerAdapter adapter =
//        new RecyclerAdapter(getActivity(), mediaStoreData, glideRequests);
//    RecyclerViewPreloader<MediaStoreData> preloader =
//        new RecyclerViewPreloader<>(glideRequests, adapter, adapter, 3);
//    recyclerView.addOnScrollListener(preloader);
//    recyclerView.setAdapter(adapter);


        //dataList = VideoLoader.loadVideos(this);

        RecyclerCommonAdapter mAdapter = new RecyclerCommonAdapter<MediaStoreData>(getActivity(),
                R.layout.layout_list_item_video, mediaStoreData, glideRequests) {
            @Override
            protected void convert(final ViewHolder holder, MediaStoreData o) {


                loadImages(o,(ImageView)holder.getView(R.id.iv_video_thumb));

            }
        };

        RecyclerViewPreloader<MediaStoreData> preloader =
                new RecyclerViewPreloader<MediaStoreData>(glideRequests, mAdapter, mAdapter, 3);
        recyclerView.addOnScrollListener(preloader);
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onLoaderReset(Loader<List<MediaStoreData>> loader) {
        // Do nothing.
    }
}
