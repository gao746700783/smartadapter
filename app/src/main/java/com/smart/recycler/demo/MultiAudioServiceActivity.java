package com.smart.recycler.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.smart.adapter.recyclerview.CommonAdapter;
import com.smart.adapter.recyclerview.ViewHolder;
import com.smart.recycler.R;
import com.smart.view.decoration.DividerItemDecoration;
import com.smart.view.recyclerview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MultiAudioServiceActivity extends AppCompatActivity
        implements AudioPlayBroadcastReceiver.AudioPlayCallback {

    private static final String TAG = "MultiAudioActivity";

    LinearLayout mEmptyView;
    EmptyRecyclerView rv_audio_multi;
    CommonAdapter<AudioInfo> mAdapter;

    private List<AudioInfo> dataList = new ArrayList<>();
    private int oldIndex = -1;
    private int curIndex = -1;

    AudioPlayBroadcastReceiver mAudioPlayReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play_multi);

        mAppContext = this;

        dataList.add(new AudioInfo("audio0", "http://96.ierge.cn/13/199/398121.mp3"));
        dataList.add(new AudioInfo("audio1", "http://radio.intergalacticfm.com/1"));
        dataList.add(new AudioInfo("audio2", "http://radio.intergalacticfm.com/2"));
        dataList.add(new AudioInfo("audio3", "http://radio.intergalacticfm.com/3"));
        dataList.add(new AudioInfo("audio4", "http://radio.intergalacticfm.com/4"));
        dataList.add(new AudioInfo("audio5", "http://radio.intergalacticfm.com/5"));
        dataList.add(new AudioInfo("audio6", "http://radio.intergalacticfm.com/6"));
        dataList.add(new AudioInfo("audio7", "http://radio.intergalacticfm.com/7"));
        dataList.add(new AudioInfo("audio8", "http://96.ierge.cn/13/199/398121.mp3"));

        rv_audio_multi = (EmptyRecyclerView) findViewById(R.id.rv_audio_multi);
        mEmptyView = (LinearLayout) findViewById(R.id.linear_empty);
        mAdapter = new CommonAdapter<AudioInfo>(this, R.layout.layout_list_item_audio_play_multi, dataList) {
            @Override
            protected void convert(final ViewHolder holder, AudioInfo o) {
                holder.setText(R.id.tv_multi_audio_name, o.getName());
                holder.setOnClickListener(R.id.iv_multi_play_pause, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // play options
                        oldIndex = curIndex;
                        curIndex = holder.getLayoutPosition();

                        playControl(curIndex);
                    }
                });
                boolean isPlaying = false;
                if (curIndex == holder.getLayoutPosition() && o.getState() == AudioStreamService.STATE_PLAYING) {
                    isPlaying = true;
                }
                holder.setImageResource(R.id.iv_multi_play_pause, isPlaying ? R.drawable.pause : R.drawable.play);
            }
        };
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_audio_multi.setLayoutManager(mLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        rv_audio_multi.addItemDecoration(itemDecoration);

        rv_audio_multi.setAdapter(mAdapter);
        rv_audio_multi.setEmptyView(mEmptyView);


        // bind service
        Intent audioService = new Intent(this.mAppContext.getApplicationContext(), AudioStreamService.class);
        bindService(audioService, mSrvConnection, Context.BIND_AUTO_CREATE);

        // init receiver
        //mAudioPlayReceiver = new AudioPlayBroadcastReceiver(this);
        mAudioPlayReceiver = new AudioPlayBroadcastReceiver();
        mAudioPlayReceiver.setOnAudioPlayCallback(this);
        // register receiver
        AudioPlayBroadcastReceiver.register(this, mAudioPlayReceiver);

    }

    private void playControl(int curIndex) {
        int state = 9999999;

        if (curIndex == -1 || curIndex >= dataList.size()) {
            Log.e(TAG, "current selected index not right");
            return;
        }

        if (mAudioPlayerSrv == null) {
            Log.e(TAG, "mAudioPlayerSrv is null");
            return;
        }

        try {
            state = mAudioPlayerSrv.getCurrState();

            if (curIndex != oldIndex) {
                if (state != AudioStreamService.STATE_NOT_PLAYING) {
                    mAudioPlayerSrv.stop();
                }
            }

            if (state == AudioStreamService.STATE_NOT_PLAYING) {
                Log.i(TAG, "Media Player state is NOT_PLAYING");

                AudioInfo audio = dataList.get(curIndex);
                mAudioPlayerSrv.play(audio.getUrl());
                audio.setState(AudioStreamService.STATE_PLAYING);
            } else {
                mAudioPlayerSrv.stop();
            }

            if (state == AudioStreamService.STATE_PLAYING) {
                Log.i(TAG, "Media Player state is PLAYING");
                mAudioPlayerSrv.stop();
                AudioInfo audio = dataList.get(curIndex);
                mAudioPlayerSrv.play(audio.getUrl());
                audio.setState(AudioStreamService.STATE_NOT_PLAYING);
            }

            mAdapter.notifyDataSetChanged();

        } catch (RemoteException e) {
            Log.e(TAG, "Error connecting to AudioService: ", e);
        }
        Log.i(TAG, "play pause button clicked!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        AudioPlayBroadcastReceiver.unregister(this, mAudioPlayReceiver);

        //
        unbindService(mSrvConnection);
    }

    @Override
    public void updateProgress(int progress) {
        Log.i(TAG, "Audio play progress ，percent:" + progress);
//        pb_audio_play.setProgress(progress);
    }

    @Override
    public void updateState(int state, int bufferPercent) {
        if (state == AudioStreamService.STATE_NOT_PLAYING) {

            AudioInfo audio = dataList.get(curIndex);
            audio.setState(AudioStreamService.STATE_NOT_PLAYING);

        } else if (state == AudioStreamService.STATE_PLAYING) {
            AudioInfo audio = dataList.get(curIndex);
            audio.setState(AudioStreamService.STATE_PLAYING);
        } else if (state == AudioStreamService.STATE_BUFFERING) {
            Log.i(TAG, "Audio play state buffering ，percent:" + bufferPercent);
            //pb_audio_play.setSecondaryProgress(bufferPercent);
        }
        mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemChanged(curIndex);
    }

    public Context mAppContext;

    private static final String IFM2 = "http://radio.intergalacticfm.com/2";
    private static final String IFM1 = "http://radio.intergalacticfm.com/1";
    private static final String IFM = "http://96.ierge.cn/13/199/398121.mp3";
    //public URL mTrackUrl;

    public IAudioStreamService mAudioPlayerSrv = null;

    private ServiceConnection mSrvConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName classname, IBinder service) {
            mAudioPlayerSrv = IAudioStreamService.Stub.asInterface(service);
        }

        public void onServiceDisconnected(ComponentName name) {
            mAudioPlayerSrv = null;
        }
    };


}
