package com.smart.recycler.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.smart.recycler.R;

public class SingleAudioServiceActivity extends AppCompatActivity
        implements AudioPlayBroadcastReceiver.AudioPlayCallback {

    private static final String TAG = "AudioPlayActivity";

    ProgressBar pb_audio_play;
    ImageView iv_play_pause;

    AudioPlayBroadcastReceiver mAudioPlayReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play);

        mAppContext = this;

        pb_audio_play = (ProgressBar) findViewById(R.id.pb_audio_play);
        iv_play_pause = (ImageView) findViewById(R.id.iv_play_pause);

        iv_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = 9999999;
                if (mAudioPlayerSrv == null) {
                    return;
                }

                try {
                    state = mAudioPlayerSrv.getCurrState();

                    if (state == AudioStreamService.STATE_NOT_PLAYING) {
                        Log.i(TAG, "Media Player state is NOT_PLAYING");
                        mAudioPlayerSrv.play(IFM);
                        iv_play_pause.setImageResource(R.drawable.pause);
                    } else {
                        mAudioPlayerSrv.stop();
                        iv_play_pause.setImageResource(R.drawable.play);
                    }

                    if (state == AudioStreamService.STATE_PLAYING) {
                        Log.i(TAG, "Media Player state is PLAYING");
                        mAudioPlayerSrv.stop();

                        iv_play_pause.setImageResource(R.drawable.play);
                    }

                } catch (RemoteException e) {
                    Log.e(TAG, "Error connecting to AudioService: ", e);
                }
                Log.i(TAG, "play pause button clicked!");

            }
        });

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
        pb_audio_play.setProgress(progress);
    }

    @Override
    public void updateState(int state, int bufferPercent) {
        if (state == AudioStreamService.STATE_NOT_PLAYING) {
            iv_play_pause.setImageResource(R.drawable.play);
        } else if (state == AudioStreamService.STATE_PLAYING) {
            iv_play_pause.setImageResource(R.drawable.pause);
        } else if (state == AudioStreamService.STATE_BUFFERING) {
            Log.i(TAG, "Audio play state buffering ，percent:" + bufferPercent);
            //pb_audio_play.setSecondaryProgress(bufferPercent);
        }
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
