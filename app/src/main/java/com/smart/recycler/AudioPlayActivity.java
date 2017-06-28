package com.smart.recycler;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

public class AudioPlayActivity extends AppCompatActivity {
    private static final String TAG = "AudioPlayActivity";

    ProgressBar pb_audio_play;
    ImageView iv_play_pause;

    MediaPlayer mMediaPlayer;

    //setDataSource(String path);//指定装载path路径所代表的文件
    //setDataSource(FileDescriptor fs, long offset, long length);
    //指定装载fd所代表的文件中从offset开始，长度为length的文件内容。
    //setDataSource(FileDescriptor fd);//指定装载fd所代表的文件
    //setDataSource(Context, Uri);//指定装载Uri所代表的文件

    boolean isMediaPlayerPrepared = false;

    private final Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play);

        pb_audio_play = (ProgressBar) findViewById(R.id.pb_audio_play);
        iv_play_pause = (ImageView) findViewById(R.id.iv_play_pause);

        iv_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMediaPlayerPrepared) {
                    Toast.makeText(AudioPlayActivity.this, "未装载完毕！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mMediaPlayer == null){
                    initMediaPlayer();
                }

                if (mMediaPlayer.isPlaying()) {
                    iv_play_pause.setImageResource(R.drawable.play);
                    //
                    mMediaPlayer.pause();
                } else {
                    mMediaPlayer.start();
                    iv_play_pause.setImageResource(R.drawable.pause);

                    // 2 seconds delay
                    handler.post(runnable);

                }
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                // recycle execute
                handler.postDelayed(runnable, 500);

                // do something
                if (mMediaPlayer.isPlaying()) {
                    int position = mMediaPlayer.getCurrentPosition();
                    pb_audio_play.setProgress(position);

                    pb_audio_play.setSecondaryProgress(90);
                }
            }
        };

        this.initMediaPlayer();

    }

    private void initMediaPlayer() {

        mMediaPlayer = new MediaPlayer();

        String dizhi = "http://96.ierge.cn/13/199/398121.mp3";
        Uri uri = Uri.parse(dizhi);
        try {
            mMediaPlayer.setDataSource(this, uri);
            //mMediaPlayer.setDataSource(this,Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.apple" ));
            // 音频流使用 prepareAsync
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();

        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }

        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                pb_audio_play.setSecondaryProgress(percent);

                int currentProgress = mMediaPlayer.getCurrentPosition() / mMediaPlayer.getDuration();

                Log.v(TAG, mMediaPlayer.getCurrentPosition() + " % play " + mMediaPlayer.getDuration());
                Log.v(TAG, currentProgress + " % play, " + percent + "% buffer");
            }
        });
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //
                isMediaPlayerPrepared = true;
                Toast.makeText(AudioPlayActivity.this, "ready for playback！", Toast.LENGTH_SHORT).show();

                //
                int max = mMediaPlayer.getDuration();
                pb_audio_play.setMax(max);

            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "completed!");
                iv_play_pause.setImageResource(R.drawable.play);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //
        handler.removeCallbacks(runnable);

        // release mediaPlayer
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }

    }


}
