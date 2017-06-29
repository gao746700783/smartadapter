package com.smart.recycler.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Description: AudioPlayBroadcastReceiver
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/29 下午12:27 <br/>
 */
public class AudioPlayBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "AudioPlayReceiver";

    public static final String ACTION_UPDATE_PROGRESS = "com.smart.recycler.demo.ACTION_UPDATE_PROGRESS";
    public static final String ACTION_PLAY_STATE_CHANGED = "com.smart.recycler.demo.ACTION_PLAY_STATE_CHANGED";

    public static final String EXTRA_UPDATE_PROGRESS = "com.smart.recycler.demo.EXTRA_UPDATE_PROGRESS";
    public static final String EXTRA_PLAY_STATE = "com.smart.recycler.demo.EXTRA_PLAY_STATE";
    public static final String EXTRA_PLAY_STATE_BUFFERING_LEVEL = "com.smart.recycler.demo.EXTRA_PLAY_STATE_BUFFERING_LEVEL";

    public AudioPlayBroadcastReceiver() {
    }

    public AudioPlayBroadcastReceiver(AudioPlayCallback audioPlayCallback) {
        this.mAudioPlayCallback = audioPlayCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Log.i(TAG, "onReceive called!");
        if (intent.getAction() == null) {
            return;
        }

        if (mAudioPlayCallback == null) {
            Log.e(TAG, "Note that your audioPlayCallback is null,initialize or call setter method first.");
            return;
        }

        if (intent.getAction().equals(ACTION_UPDATE_PROGRESS)) {
            int progress = intent.getIntExtra(EXTRA_UPDATE_PROGRESS, 0);
            mAudioPlayCallback.updateProgress(progress);
        } else if (intent.getAction().equals(ACTION_PLAY_STATE_CHANGED)) {
            int state = intent.getIntExtra(EXTRA_PLAY_STATE, -1);
            int bufferPercent;
            if (state == AudioStreamService.STATE_BUFFERING) {
                bufferPercent = intent.getIntExtra(EXTRA_PLAY_STATE_BUFFERING_LEVEL, 0);
                mAudioPlayCallback.updateState(state, bufferPercent);
            }
        }

    }

    public static void register(Context context, BroadcastReceiver mAudioPlayReceiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AudioPlayBroadcastReceiver.ACTION_UPDATE_PROGRESS);
        filter.addAction(AudioPlayBroadcastReceiver.ACTION_PLAY_STATE_CHANGED);
        context.registerReceiver(mAudioPlayReceiver, filter);
    }

    public static void unregister(Context context, BroadcastReceiver mAudioPlayReceiver) {
        if (null != mAudioPlayReceiver) {
            context.unregisterReceiver(mAudioPlayReceiver);
        }
    }

    public void setOnAudioPlayCallback(AudioPlayCallback audioPlayCallback) {
        this.mAudioPlayCallback = audioPlayCallback;
    }

    private AudioPlayCallback mAudioPlayCallback;

    interface AudioPlayCallback {
        void updateProgress(int progress);

        void updateState(int state, int bufferPercent);
    }

}
