package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button play,stop,pause,exit;
    MediaPlayer mediaPlayer;
    int currentPosition;
    SeekBar songSeekBar;
    Thread updateSeekbar;

    TextView txtMarquee;

    TextView tv;
    String st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=(Button) findViewById(R.id.start);
        stop=(Button) findViewById(R.id.stop);
        pause=(Button) findViewById(R.id.pause);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

        txtMarquee=(TextView) findViewById(R.id.marquee);
        txtMarquee.setSelected(true);

        exit=(Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);

        songSeekBar =(SeekBar) findViewById(R.id.seekbar);
        updateSeekbar = new Thread(){
            @Override
            public void run()
            {
                int duration = mediaPlayer.getDuration();
                int position = 0;
                while(position<duration)
                {
                    try {
                        sleep(500);
                        position=mediaPlayer.getCurrentPosition();
                        songSeekBar.setProgress(position);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    @Override
    public void onClick(View view) {

        tv = findViewById(R.id.xyz);

        switch (view.getId())
        {
            case R.id.start:
                if(mediaPlayer==null)
                {
                    mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.song);
                    mediaPlayer.start();
                    songSeekBar.setMax(mediaPlayer.getDuration());
                    updateSeekbar.start();
                    songSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            mediaPlayer.seekTo(seekBar.getProgress());
                        }
                    });
                }
                else if(!mediaPlayer.isPlaying())
                {
                    mediaPlayer.seekTo(currentPosition);
                    mediaPlayer.start();
                }

                st="Playing";
                tv.setText(st);
                break;
            case R.id.stop:
                if(mediaPlayer!=null)
                { mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                currentPosition=mediaPlayer.getCurrentPosition();
                }

                st="Stopped";
                tv.setText(st);
                break;
            case R.id.pause:
                if(mediaPlayer!=null) {
                    mediaPlayer.pause();
                    currentPosition = mediaPlayer.getCurrentPosition();
                }

                st="Paused";
                tv.setText(st);
                break;
            case R.id.exit:
                finish();
                System.exit(0);
        }
    }
}
