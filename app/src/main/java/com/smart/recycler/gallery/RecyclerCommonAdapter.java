//package com.smart.recycler.gallery;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.widget.ImageView;
//
//import com.bumptech.glide.ListPreloader;
//import com.bumptech.glide.RequestBuilder;
//import com.bumptech.glide.load.Key;
//import com.bumptech.glide.signature.MediaStoreSignature;
//import com.smart.adapter.recyclerview.CommonAdapter;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * Displays {@link MediaStoreData} in a recycler view.
// */
//public class RecyclerCommonAdapter<T> extends CommonAdapter<T>
//        implements ListPreloader.PreloadSizeProvider<T>,
//        ListPreloader.PreloadModelProvider<T> {
//
//    private GlideRequests glideRequests;
//    private GlideRequest<Drawable> requestBuilder;
//    private int[] actualDimensions;
//
//    public RecyclerCommonAdapter(Context context, int layoutId, List<T> datas) {
//        super(context, layoutId, datas);
//
//        // init glide request
//        glideRequests = GlideApp.with(context);
//        // init glide request builder
//        requestBuilder = glideRequests.asDrawable().fitCenter();
//        // Indicates whether each item in the data set can be represented with a unique identifier
//        // of type {@link java.lang.Long}.
//        setHasStableIds(true);
//    }
//
//    @Override
//    public List<T> getPreloadItems(int position) {
//        return Collections.singletonList((T)mDataList.get(position));
//    }
//
//    @Override
//    public RequestBuilder<Drawable> getPreloadRequestBuilder(T item) {
//        if (item instanceof MediaStoreData) {
//            MediaStoreData dataItem = (MediaStoreData) item;
//
//            MediaStoreSignature signature =
//                    new MediaStoreSignature(dataItem.mimeType, dataItem.dateModified, dataItem.orientation);
//            return requestBuilder
//                    .clone()
//                    .signature(signature)
//                    .load(dataItem.uri);
//
//        }
//        return null;
//    }
//
//    @Override
//    public int[] getPreloadSize(T item, int adapterPosition, int perItemPosition) {
//        return actualDimensions;
//    }
//
//    /**
//     * load images
//     *
//     * @param dataItem dataItem
//     * @param image    image
//     */
//    void loadImages(MediaStoreData dataItem, ImageView image) {
//        Key signature = new MediaStoreSignature(dataItem.mimeType, dataItem.dateModified,
//                dataItem.orientation);
//        requestBuilder.clone().signature(signature)
//                .load(dataItem.uri)
//                .into(image);
//    }
//
//    GlideRequests getGlideRequests() {
//        return glideRequests;
//    }
//}
