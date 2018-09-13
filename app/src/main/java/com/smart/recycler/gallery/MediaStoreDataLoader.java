package com.smart.recycler.gallery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Loads metadata from the media store for images and videos.
 */
public class MediaStoreDataLoader extends AsyncTaskLoader<List<MediaStoreData>> {

    /**
     * image projection
     */
    private static final String[] IMAGE_PROJECTION =
            new String[]{
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.TITLE,
                    MediaStore.Images.ImageColumns.DATE_TAKEN,
                    MediaStore.Images.ImageColumns.DATE_MODIFIED,
                    MediaStore.Images.ImageColumns.MIME_TYPE,
                    MediaStore.Images.ImageColumns.ORIENTATION,
            };

    /**
     * video projection
     */
    private static final String[] VIDEO_PROJECTION =
            new String[]{
                    MediaStore.Video.VideoColumns._ID,
                    MediaStore.Video.VideoColumns.TITLE,
                    MediaStore.Video.VideoColumns.DATE_TAKEN,
                    MediaStore.Video.VideoColumns.DATE_MODIFIED,
                    MediaStore.Video.VideoColumns.MIME_TYPE,
                    "0 AS " + MediaStore.Images.ImageColumns.ORIENTATION,
            };

    /**
     * audio projection
     */
    private static final String[] AUDIO_PROJECTION =
            new String[]{
                    MediaStore.Audio.AudioColumns._ID,
                    MediaStore.Audio.AudioColumns.TITLE,
                    MediaStore.Audio.AudioColumns.DATE_ADDED,
                    MediaStore.Audio.AudioColumns.DATE_MODIFIED,
                    MediaStore.Audio.AudioColumns.MIME_TYPE,
                    "0 AS " + MediaStore.Images.ImageColumns.ORIENTATION,
            };

    private List<MediaStoreData> cached;
    private boolean observerRegistered = false;
    private final ForceLoadContentObserver forceLoadContentObserver = new ForceLoadContentObserver();

    private MediaStoreData.Type mDataType;

    public MediaStoreDataLoader(Context context) {
        this(context, MediaStoreData.Type.IMAGE);
    }

    public MediaStoreDataLoader(Context context, MediaStoreData.Type dataType) {
        super(context);
        this.mDataType = dataType;
    }

    @Override
    public void deliverResult(List<MediaStoreData> data) {
        if (!isReset() && isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (cached != null) {
            deliverResult(cached);
        }
        if (takeContentChanged() || cached == null) {
            forceLoad();
        }
        registerContentObserver();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        cached = null;
        unregisterContentObserver();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
        unregisterContentObserver();
    }

    @Override
    public List<MediaStoreData> loadInBackground() {
        List<MediaStoreData> data = new ArrayList<>();

        if (mDataType == MediaStoreData.Type.AUDIO) {
            data.addAll(queryAudios());
        } else if (mDataType == MediaStoreData.Type.IMAGE) {
            data.addAll(queryImages());
        } else if (mDataType == MediaStoreData.Type.VIDEO) {
            data.addAll(queryVideos());
        }
        Collections.sort(data, new Comparator<MediaStoreData>() {
            @Override
            public int compare(MediaStoreData mediaStoreData, MediaStoreData mediaStoreData2) {
                return Long.valueOf(mediaStoreData2.dateTaken).compareTo(mediaStoreData.dateTaken);
            }
        });
        return data;
    }

    private List<MediaStoreData> queryImages() {
        return query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.TITLE,
                MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATE_MODIFIED,
                MediaStore.Images.ImageColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStoreData.Type.IMAGE);
    }

    private List<MediaStoreData> queryVideos() {
        return query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION,
                MediaStore.Video.VideoColumns.DATE_TAKEN,
                MediaStore.Video.VideoColumns._ID, MediaStore.Video.VideoColumns.TITLE,
                MediaStore.Video.VideoColumns.DATE_TAKEN, MediaStore.Video.VideoColumns.DATE_MODIFIED,
                MediaStore.Video.VideoColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStoreData.Type.VIDEO);
    }

    private List<MediaStoreData> queryAudios() {
        return query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, AUDIO_PROJECTION,
                MediaStore.Audio.AudioColumns.DATE_ADDED,
                MediaStore.Audio.AudioColumns._ID, MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATE_ADDED, MediaStore.Audio.AudioColumns.DATE_MODIFIED,
                MediaStore.Audio.AudioColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStoreData.Type.AUDIO);
    }

    private List<MediaStoreData> query(Uri contentUri, String[] projection, String sortByCol,
                                       String idCol, String titleCol, String dateTakenCol,
                                       String dateModifiedCol, String mimeTypeCol,
                                       String orientationCol, MediaStoreData.Type type) {
        final List<MediaStoreData> data = new ArrayList<MediaStoreData>();
        Cursor cursor = getContext().getContentResolver()
                .query(contentUri, projection, null, null, sortByCol + " DESC");

        if (cursor == null) {
            return data;
        }

        try {
            final int idColNum = cursor.getColumnIndexOrThrow(idCol);
            final int titleColNum = cursor.getColumnIndexOrThrow(titleCol);
            final int dateTakenColNum = cursor.getColumnIndexOrThrow(dateTakenCol);
            final int dateModifiedColNum = cursor.getColumnIndexOrThrow(dateModifiedCol);
            final int mimeTypeColNum = cursor.getColumnIndex(mimeTypeCol);
            final int orientationColNum = cursor.getColumnIndexOrThrow(orientationCol);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColNum);
                String title = cursor.getString(titleColNum);
                long dateTaken = cursor.getLong(dateTakenColNum);
                String mimeType = cursor.getString(mimeTypeColNum);
                long dateModified = cursor.getLong(dateModifiedColNum);
                int orientation = cursor.getInt(orientationColNum);

                data.add(new MediaStoreData(id, title, Uri.withAppendedPath(contentUri, Long.toString(id)),
                        mimeType, dateTaken, dateModified, orientation, type));
            }
        } finally {
            cursor.close();
        }

        return data;
    }

    private void registerContentObserver() {
        if (!observerRegistered) {
            ContentResolver cr = getContext().getContentResolver();
            cr.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver);
            cr.registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver);
            cr.registerContentObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver);

            observerRegistered = true;
        }
    }

    private void unregisterContentObserver() {
        if (observerRegistered) {
            observerRegistered = false;

            getContext().getContentResolver().unregisterContentObserver(forceLoadContentObserver);
        }
    }
}
