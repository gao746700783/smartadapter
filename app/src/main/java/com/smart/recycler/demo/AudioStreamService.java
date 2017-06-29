package com.smart.recycler.demo;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AudioStreamService extends Service implements
        OnCompletionListener,
        OnPreparedListener,
        OnBufferingUpdateListener,
        OnErrorListener {

    private static final String TAG = "[AudioStreamService]";

    /* !------------- media player state ---------------- */
    public static final int STATE_NOT_PLAYING = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_BUFFERING = 2;


    private int mAudioSrvState;

    private MediaPlayer mMediaPlayer;

    private String mAudioTrackURL;

    //public SeekBar mSeekBar;
    //private int mSeekBarLen = 2000;
    private ProgressBarThread seekBarThread = null;

    public AudioStreamService() {
        mAudioSrvState = STATE_NOT_PLAYING;

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        seekBarThread = new ProgressBarThread();
    }

    public IBinder onBind(Intent intent) {
        return mSrvBinding;
    }

    public void onPrepared(MediaPlayer mp) {
        mAudioSrvState = STATE_PLAYING;
        Log.v(TAG, "Prepared mediaplayer for track of duration =[" + mp.getDuration() + "]");
        Time duration = new Time(mp.getDuration());
        Log.v(TAG, "Prepared mediaplayer for track of duration =[" + duration.toString() + "]");
        mMediaPlayer.start();

        //
        sendPlayBroadcast(AudioPlayBroadcastReceiver.ACTION_PLAY_STATE_CHANGED,
                AudioPlayBroadcastReceiver.EXTRA_PLAY_STATE,
                mAudioSrvState);

        //
        startBarThread();
    }

    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.v(TAG, "Buffered additional=[" + percent + "%]");
        mAudioSrvState = STATE_BUFFERING;

        Map<String, Integer> playMap = new HashMap<>();
        playMap.put(AudioPlayBroadcastReceiver.EXTRA_PLAY_STATE, mAudioSrvState);
        playMap.put(AudioPlayBroadcastReceiver.EXTRA_PLAY_STATE_BUFFERING_LEVEL, percent);
        //
        sendPlayBroadcast(AudioPlayBroadcastReceiver.ACTION_PLAY_STATE_CHANGED, playMap);
    }

    public void onCompletion(MediaPlayer mp) {
        Log.i(TAG, "Completed playback");
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mAudioSrvState = STATE_NOT_PLAYING;

        //
        sendPlayBroadcast(AudioPlayBroadcastReceiver.ACTION_PLAY_STATE_CHANGED,
                AudioPlayBroadcastReceiver.EXTRA_PLAY_STATE,
                mAudioSrvState);
        //
        stopBarThread();
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.e(TAG, "unknown media error what=[" + what + "] extra=[" + extra + "]");
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.e(TAG, "Streaming source Server died what=[" + what + "] extra=[" + extra + "]");
                break;
            default:
                Log.e(TAG, "Default Problems what=[" + what + "] extra=[" + extra + "]");
        }
        return false;
    }

    /**
     * sendPlayBroadcast
     *
     * @param action    action
     * @param extraName extraName
     * @param extraData extraData
     */
    private void sendPlayBroadcast(String action, String extraName, int extraData) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(extraName, extraData);
        sendBroadcast(intent);
    }

    /**
     * sendPlayBroadcast
     *
     * @param action    action
     * @param paramsMap paramsMap
     */
    private void sendPlayBroadcast(String action, Map<String, Integer> paramsMap) {
        Intent intent = new Intent();
        intent.setAction(action);
        //
        for (Map.Entry<String, Integer> entry : paramsMap.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }

        sendBroadcast(intent);
    }

    /*! -- methods in aidl for multi-thread communication -- ***/

    public void play(String audioTrackURL) {
        Log.i(TAG, "Playing track at URL=[" + audioTrackURL + "]");
        mAudioTrackURL = audioTrackURL;

        if (mAudioSrvState == STATE_NOT_PLAYING) {
            try {
                mMediaPlayer.setDataSource(mAudioTrackURL);
                mMediaPlayer.prepareAsync();
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "AudioTrackUrl seems to be incorrectly formatted", e);
            } catch (IllegalStateException e) {
                Log.e(TAG, "MediaPlayer is in an illegal state", e);
            } catch (IOException e) {
                Log.e(TAG, "MediaPlayer failed due to exception", e);
            }
        }

    }

    public void stop() {
        Log.i(TAG, "Call to stop streaming audio");

        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                Log.i(TAG, "media player was playing and is now Stopping");
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mAudioSrvState = STATE_NOT_PLAYING;

                //
                stopBarThread();
            }
        }
    }

    public int getCurrentPosition() {
        if (mMediaPlayer == null) {
            return 0;
        }
        return mMediaPlayer.getCurrentPosition();
    }

    public long getDuration() {
        if (mMediaPlayer == null) {
            return 1;
        }
        return mMediaPlayer.getDuration();
    }

    public String getFileName() {
        return null;
    }

    public int getState() {
        return mAudioSrvState;
    }

    /**
     * Description: IAudioStreamService.Stub
     * <p>
     * User: qiangzhang <br/>
     * Date: 2017/6/28 下午5:12 <br/>
     */
    private final IAudioStreamService.Stub mSrvBinding = new IAudioStreamService.Stub() {

        public void play(String url) throws RemoteException {
            AudioStreamService.this.play(url);
        }

        public void stop() {
            AudioStreamService.this.stop();
        }

        public String getFileName() {
            return AudioStreamService.this.getFileName();
        }

        public int getCurrPosition() {
            return AudioStreamService.this.getCurrentPosition();
        }

        public long getDuration() {
            return AudioStreamService.this.getDuration();
        }

        public int getCurrState() {
            return AudioStreamService.this.getState();
        }

    };

    /**
     * Description: ProgressBarThread
     * <p>
     * User: qiangzhang <br/>
     * Date: 2017/6/29 上午9:54 <br/>
     */
    private class ProgressBarThread extends Thread {
        boolean flag = true;

        long duration = 1;
        int position = 0;
        int progress = 0;

        // current song's playing time
        private ProgressBarThread() {
        }

        private void off() {
            flag = false;
        }

        public void run() {
            flag = true;
            if (mMediaPlayer.isPlaying()) {
                duration = getDuration();
            }

            progress = 0;
            position = 0;

            // while
            while (flag) {

                position = getCurrentPosition();
                if (duration != 0) {
                    progress = (int) (100 * position / duration);
                }

                //send position
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // change progress
                sendPlayBroadcast(AudioPlayBroadcastReceiver.ACTION_UPDATE_PROGRESS,
                        AudioPlayBroadcastReceiver.EXTRA_UPDATE_PROGRESS,
                        progress);

            }
        }
    }

    private void startBarThread() {
        if (seekBarThread != null) {
            seekBarThread.off();
        }
        seekBarThread = new ProgressBarThread();
        seekBarThread.start();
    }

    private void stopBarThread() {
        if (seekBarThread != null) {
            seekBarThread.off();
            seekBarThread = null;
        }
    }


}