package com.smart.recycler.demo.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

/**
 * Description: AudioLoader
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/27 上午10:00 <br/>
 */
public class AudioLoader extends CursorLoader {

    private static final String TAG = "AudioLoader";

    private static AudioLoader musicLoader;

    //Uri，指向external的database
    private static final Uri CONTENT_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    //private static final Uri CONTENT_URI = MediaStore.Audio.Media.getContentUriForPath(
    //        Environment.getExternalStorageDirectory().getAbsolutePath());
    //private static final Uri CONTENT_URI = MediaStore.Audio.Media.getContentUri("external");
    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private static final String[] PROJECTION = {
            Media._ID,
            Media.DISPLAY_NAME,
            Media.TITLE,
            Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE
    };
    private static final String WHERE = "mime_type in ('audio/mpeg','audio/x-ms-wma') " +
            "and _display_name <> 'audio' and is_music > 0 ";

    private static final String SELECTION = Media.DATA + " like ? ";
    private static final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
    private static final String[] SELECTION_ARGS = {path + "%"};

    private static final String SORT_ORDER = Media.DEFAULT_SORT_ORDER;


    public static AudioLoader instance(Context context) {
        if (musicLoader == null) {
            musicLoader = new AudioLoader(context);
            Log.i(TAG, "path:" + path);
        }
        return musicLoader;
    }

    private AudioLoader(Context context) {
        super(context, CONTENT_URI, PROJECTION, SELECTION, SELECTION_ARGS, SORT_ORDER);
    }


    /**
     * 查询专辑封面图片uri
     */
    private static String getCoverUri(Context context, long albumId) {
        String uri = null;
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://media/external/audio/albums/" + albumId),
                new String[]{"album_art"}, null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
            uri = cursor.getString(0);
            cursor.close();
        }


        //CoverLoader.getInstance().loadThumbnail(uri);
        return uri;
    }

    //    public List<AudioModel> getMusicList(Cursor cursor) {
    //
    //        if (cursor == null) {
    //            Log.v(TAG, "Music Loader cursor == null.");
    //        } else if (!cursor.moveToFirst()) {
    //            Log.v(TAG, "Music Loader cursor.moveToFirst() returns false.");
    //        } else {
    //            int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
    //            int albumCol = cursor.getColumnIndex(Media.ALBUM);
    //            int idCol = cursor.getColumnIndex(Media._ID);
    //            int durationCol = cursor.getColumnIndex(Media.DURATION);
    //            int sizeCol = cursor.getColumnIndex(Media.SIZE);
    //            int artistCol = cursor.getColumnIndex(Media.ARTIST);
    //            int urlCol = cursor.getColumnIndex(Media.DATA);
    //            do {
    //                String title = cursor.getString(displayNameCol);
    //                String album = cursor.getString(albumCol);
    //                long id = cursor.getLong(idCol);
    //                int duration = cursor.getInt(durationCol);
    //                long size = cursor.getLong(sizeCol);
    //                String artist = cursor.getString(artistCol);
    //                String url = cursor.getString(urlCol);
    //
    //                AudioModel musicInfo = new AudioModel();
    ////                musicInfo.setAlbum(album);
    ////                musicInfo.setDuration(duration);
    ////                musicInfo.setSize(size);
    ////                musicInfo.setArtist(artist);
    ////                musicInfo.setUrl(url);
    //                musicList.add(musicInfo);
    //
    //            } while (cursor.moveToNext());
    //        }
    //
    //        return musicList;
    //    }
    //
    //    public Uri getMusicUriById(long id) {
    //        Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
    //        return uri;
    //    }


}
