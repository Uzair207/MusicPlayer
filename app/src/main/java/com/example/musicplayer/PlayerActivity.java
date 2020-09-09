package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.musicplayer.MainActivity.musicFiles;

public class PlayerActivity extends AppCompatActivity {
    TextView songName, durationPlayed, durationTotal;
    ImageView coverArt, nextBtn, prevBtn, backBtn;
    FloatingActionButton playPauseBtn;
    SeekBar seekBar;
int position = -1;
static ArrayList<MusicFiles> listSongs = new ArrayList<>();
static Uri uri;
static MediaPlayer mediaPlayer;
private Handler handler = new Handler();
private Thread playPause, prevThread, nextThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        getIntendMethod();
        songName.setText(listSongs.get(position).getTitle());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                openPrevActivity();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                 //   seekBar.setProgress(i);
                    mediaPlayer.seekTo(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
         //       mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

       PlayerActivity.this.runOnUiThread(new Runnable() {
           int durationtotal = Integer.parseInt(listSongs.get(position).getDuration())/1000;
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000 ;
                    seekBar.setProgress(mCurrentPosition);
                    durationPlayed.setText(formattedTime(mCurrentPosition));
                    durationTotal.setText(formattedTime(durationtotal));
                }
                handler.postDelayed(this,900);
            }
        });
    }

    private void openPrevActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1)<0?(listSongs.size()-1):(position-1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            songName.setText(listSongs.get(position).getTitle());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                int durationtotal = Integer.parseInt(listSongs.get(position).getDuration())/1000;
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000 ;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,900);
                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
            mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position=((position-1)<0?(listSongs.size()-1):(position-1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            songName.setText(listSongs.get(position).getTitle());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                int durationtotal = Integer.parseInt(listSongs.get(position).getDuration())/1000;
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000 ;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,900);
                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow);
        }
    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            position=(position+1)%listSongs.size();
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            songName.setText(listSongs.get(position).getTitle());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                int durationtotal = Integer.parseInt(listSongs.get(position).getDuration())/1000;
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000 ;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,900);
                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
            mediaPlayer.start();
        }
        else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position=(position+1)%listSongs.size();
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            songName.setText(listSongs.get(position).getTitle());
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                int durationtotal = Integer.parseInt(listSongs.get(position).getDuration())/1000;
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000 ;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,900);
                }
            });
            playPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow);
        }
    }

    private void playThreadBtn() {
        playPause = new Thread(){
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playPause.start();
    }

    private void playPauseBtnClicked() {
        if(mediaPlayer.isPlaying()){
            playPauseBtn.setImageResource(R.drawable.ic_baseline_play_arrow);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                int durationtotal = Integer.parseInt(listSongs.get(position).getDuration())/1000;
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000 ;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,900);
                }
            });
        }
        else{
            playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                int durationtotal = Integer.parseInt(listSongs.get(position).getDuration())/1000;
                @Override
                public void run() {
                    if(mediaPlayer!=null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000 ;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,900);
                }
            });
        }
    }

    private String formattedTime(int mCurrentPosition) {
        String totalOut= "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition%60);
        String mins = String.valueOf(mCurrentPosition/60);
        totalOut = mins+ ":"+ seconds;
        totalNew = mins+ ":"+"0"+seconds;
        if(seconds.length()==1){
            return totalNew;
        }
        else{
             return totalOut;
        }
    }

    private void getIntendMethod(){
    position = getIntent().getIntExtra("position",-1);
    listSongs = musicFiles;
    if(listSongs!=null){
        playPauseBtn.setImageResource(R.drawable.ic_baseline_pause);
        uri = Uri.parse(listSongs.get(position).getPath());
    }
    if(mediaPlayer!=null){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();
    }
    else{
        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();
    }
    seekBar.setMax(mediaPlayer.getDuration()/1000);
    }

    private void initViews() {
        songName = findViewById(R.id.song_name);
        durationPlayed = findViewById(R.id.durationplayed);
        durationTotal = findViewById(R.id.durationtotal);
        coverArt = findViewById(R.id.cover_art);
        nextBtn = findViewById(R.id.next);
        prevBtn = findViewById(R.id.prev);
        backBtn = findViewById(R.id.back_btn);
        playPauseBtn = findViewById(R.id.play_pause_button);
        seekBar = findViewById(R.id.seekbar);
    }
}