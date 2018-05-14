package com.smart.recycler.demo.loader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.IConverter;
import com.smart.adapter.recyclerview.IHolder;
import com.smart.recycler.R;
import com.smart.view.decoration.DividerItemDecoration;
import com.smart.view.recyclerview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: LoaderAudioActivity
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/27 上午10:25 <br/>
 */
public class LoaderVideoActivity extends AppCompatActivity {

    LinearLayout mEmptyView;
    EmptyRecyclerView rv_video_multi;
    CommonAdapter<VideoModel> mAdapter;

    private List<VideoModel> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        rv_video_multi = (EmptyRecyclerView) findViewById(R.id.rv_video_multi);
        mEmptyView = (LinearLayout) findViewById(R.id.linear_empty);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_video_multi.setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        rv_video_multi.addItemDecoration(itemDecoration);


        dataList = VideoLoader.loadVideos(this);

        mAdapter = new VideoThumbAdapter<VideoModel>(this, R.layout.layout_list_item_video, dataList)
                .bindViewAndData(new IConverter<VideoModel>() {
                    @Override
                    public void convert(IHolder holder, VideoModel o, int position) {
                        //holder.setText(R.id.tv_multi_audio_name, o.getName());
                        holder.setText(R.id.tv_video_title, o.getTitle());
                        holder.setText(R.id.tv_video_artist, o.getFilePath());

                        //holder.setImageResource(R.id.iv_video_thumb,o.getFilePath());
                        ImageView ivVideo = holder.getView(R.id.iv_video_thumb);
                        //ivVideo.setImageURI(Uri.parse(o.getFilePath()));
                        ivVideo.setTag(o.getFilePath());

                        //this.loadThumb(ivVideo, o.getFilePath());
                    }
                });

        rv_video_multi.setAdapter(mAdapter);

        rv_video_multi.setEmptyView(mEmptyView);

    }

}
