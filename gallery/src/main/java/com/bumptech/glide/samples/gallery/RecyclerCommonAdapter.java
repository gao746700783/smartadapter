package com.bumptech.glide.samples.gallery;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.smart.adapter.recyclerview.CommonAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Displays {@link MediaStoreData} in a recycler view.
 */
public abstract class RecyclerCommonAdapter<T> extends CommonAdapter<T>
        implements ListPreloader.PreloadSizeProvider<T>,
        ListPreloader.PreloadModelProvider<T> {

    private GlideRequest<Drawable> requestBuilder;
    private int[] actualDimensions;

    public RecyclerCommonAdapter(Context context, int layoutId, List<T> datas, GlideRequests glideRequests) {
        super(context, layoutId, datas);
        requestBuilder = glideRequests.asDrawable().fitCenter();

        setHasStableIds(true);

    }

    public RecyclerCommonAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public List<T> getPreloadItems(int position) {
        return Collections.singletonList(mDataList.get(position));
    }

    @Override
    public RequestBuilder<Drawable> getPreloadRequestBuilder(T item) {
        if (item instanceof MediaStoreData) {
            MediaStoreData dataItem = (MediaStoreData) item;

            MediaStoreSignature signature =
                    new MediaStoreSignature(dataItem.mimeType, dataItem.dateModified, dataItem.orientation);
            return requestBuilder
                    .clone()
                    .signature(signature)
                    .load(dataItem.uri);

        }
        return null;
    }

    @Override
    public int[] getPreloadSize(T item, int adapterPosition, int perItemPosition) {
        return actualDimensions;
    }

    public void loadImages(MediaStoreData dataItem,ImageView image){
        MediaStoreSignature signature =
                new MediaStoreSignature(dataItem.mimeType, dataItem.dateModified, dataItem.orientation);
        requestBuilder
                .clone()
                .signature(signature)
                .load(dataItem.uri)
                .into(image);
    }

}
