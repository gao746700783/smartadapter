package com.smart.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.smart.recycler.demo.AudioPlayActivity;
import com.smart.recycler.demo.MultiAudioServiceActivity;
import com.smart.recycler.demo.SingleAudioServiceActivity;
import com.smart.recycler.demo.loader.LoaderAudioActivity;
import com.smart.recycler.demo.loader.LoaderVideoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initToolbar();

        findViewById(R.id.btn_base_use).setOnClickListener(this);
        findViewById(R.id.btn_pull_up_down).setOnClickListener(this);
        findViewById(R.id.btn_drag_n_drop).setOnClickListener(this);
        findViewById(R.id.btn_drag_n_drop_grid).setOnClickListener(this);
        findViewById(R.id.btn_audio_list).setOnClickListener(this);
        findViewById(R.id.btn_video_list).setOnClickListener(this);
        findViewById(R.id.btn_audio_play).setOnClickListener(this);
        findViewById(R.id.btn_audio_play_single).setOnClickListener(this);
        findViewById(R.id.btn_audio_play_multi).setOnClickListener(this);
        findViewById(R.id.btn_base_anim).setOnClickListener(this);
        findViewById(R.id.btn_base_search).setOnClickListener(this);
        findViewById(R.id.btn_base_multi).setOnClickListener(this);
        findViewById(R.id.btn_base_header).setOnClickListener(this);
        findViewById(R.id.btn_base_swipeRefresh).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_base_use:
                startActivity(new Intent(this, BaseUseActivity.class));
                break;
            case R.id.btn_pull_up_down:
                startActivity(new Intent(this, PullUpDownActivity.class));
                break;
            case R.id.btn_drag_n_drop:
                startActivity(new Intent(this, DragnDropActivity.class));
                break;
            case R.id.btn_drag_n_drop_grid:
                startActivity(new Intent(this, DragnDropGridActivity.class));
                break;
            case R.id.btn_audio_list:
                startActivity(new Intent(this, LoaderAudioActivity.class));
                break;
            case R.id.btn_video_list:
                startActivity(new Intent(this, LoaderVideoActivity.class));
                break;
            case R.id.btn_audio_play:
                startActivity(new Intent(this, AudioPlayActivity.class));
                break;
            case R.id.btn_audio_play_single:
                startActivity(new Intent(this, SingleAudioServiceActivity.class));
                break;
            case R.id.btn_audio_play_multi:
                startActivity(new Intent(this, MultiAudioServiceActivity.class));
                break;
            case R.id.btn_base_anim:
                startActivity(new Intent(this, AnimActivity.class));
                break;
            case R.id.btn_base_search:
                startActivity(new Intent(this, SearchViewActivity.class));
                break;
            case R.id.btn_base_multi:
                startActivity(new Intent(this, MultiItemActivity.class));
                break;
            case R.id.btn_base_header:
                startActivity(new Intent(this, HeaderFooterActivity.class));
                break;
            case R.id.btn_base_swipeRefresh:
                startActivity(new Intent(this, SwipeRefreshViewActivity.class));
                break;
            default:
                break;
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator();
            //actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }


}
