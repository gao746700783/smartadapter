package com.smart.recycler.loader;

import android.content.Context;
import android.database.Cursor;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: AudioLoader
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/27 上午10:00 <br/>
 */
public class VideoLoader {

    private static final String TAG = "VideoLoader";

    public static List<VideoModel> loadVideos(Context context) {

        String[] thumbColumns = new String[]{
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID
        };

        String[] mediaColumns = new String[]{
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE
        };

        //首先检索SDcard上所有的video

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

        List<VideoModel> videoList = new ArrayList<>();

        if (cursor == null) {
            Log.v(TAG, "Video Loader cursor == null.");
        } else if (!cursor.moveToFirst()) {
            Log.v(TAG, "Video Loader cursor.moveToFirst() returns false.");
        } else if (cursor.moveToFirst()) {
            do {

                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));

                //获取当前Video对应的Id，然后根据该ID获取其Thumb
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String selection = MediaStore.Video.Thumbnails.VIDEO_ID + "=?";
                String[] selectionArgs = new String[]{id + ""};

                Cursor thumbCursor = context.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs, null);

                String thumbPath = null;
                if (thumbCursor != null && thumbCursor.moveToFirst()) {
                    thumbPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                    ThumbnailUtils.createVideoThumbnail(thumbPath,MediaStore.Images.Thumbnails.MINI_KIND);
                    // close thumbCursor
                    thumbCursor.close();
                }


                //然后将其加入到videoList
                videoList.add(new VideoModel(filePath, mimeType, thumbPath, title));

            } while (cursor.moveToNext());

            // close cursor
            cursor.close();
        }

        return videoList;

    }

}
