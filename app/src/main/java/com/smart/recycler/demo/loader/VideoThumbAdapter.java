package com.smart.recycler.demo.loader;

import android.content.Context;
import android.widget.ImageView;

import com.smart.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by qiangzhang on 2017/6/30.
 */

public class VideoThumbAdapter<T> extends CommonAdapter<T> {

    VideoThumbLoader mVideoThumbLoader;

    public VideoThumbAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
        mVideoThumbLoader = new VideoThumbLoader();
    }

    public void loadThumb(ImageView ivVideo,String path){
        mVideoThumbLoader.showThumbByAsynctack(path, ivVideo);
    }

}
