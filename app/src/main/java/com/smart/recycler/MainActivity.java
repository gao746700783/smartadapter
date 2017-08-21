package com.smart.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        findViewById(R.id.btn_base_use).setOnClickListener(this);
        findViewById(R.id.btn_pull_up_down).setOnClickListener(this);
        findViewById(R.id.btn_drag_n_drop).setOnClickListener(this);
        findViewById(R.id.btn_drag_n_drop_grid).setOnClickListener(this);
        findViewById(R.id.btn_audio_list).setOnClickListener(this);
        findViewById(R.id.btn_video_list).setOnClickListener(this);
        findViewById(R.id.btn_audio_play).setOnClickListener(this);
        findViewById(R.id.btn_audio_play_single).setOnClickListener(this);
        findViewById(R.id.btn_audio_play_multi).setOnClickListener(this);

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
            default:
                break;
        }

    }
}
