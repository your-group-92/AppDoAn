package com.example.appgiaitri;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PlayMusic extends AppCompatActivity {

    static MediaPlayer mediaPlayer;

    ArrayList<File> mySongs;

    TextView txtTitle, txtTimeStart, txtTimeEnd;

    SeekBar skbarSong;

    ImageView imgdisc;

    ImageButton imgbtnPrev, imgbtnPlay, imgbtnStop, imgbtnNext;

    Animation animation;

    int position = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        overridePendingTransition(R.anim.slider_down,R.anim.slider_up);
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();

        Bundle b = i.getExtras();

        mySongs = (ArrayList) b.getParcelableArrayList("songlist");

        position = b.getInt("pos",0);

        animation = AnimationUtils.loadAnimation(this,R.anim.disc_rotate);

        AnhXa();

        KhoiTao();

        CapNhatTimeSong();

        mediaPlayer.start();

        imgdisc.startAnimation(animation);

        imgbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()==true){
                    //nếu đang phát -> pause -> đổi hình play
                    mediaPlayer.pause();

                    imgbtnPlay.setImageResource(R.drawable.play64);
                    imgdisc.clearAnimation();
                }else {
                    //đang ngừng -> phát -> đổi hình pause
                    mediaPlayer.start();
                    imgbtnPlay.setImageResource(R.drawable.pause64);
                    imgdisc.startAnimation(animation);
                }
                SetTimeEnd();
            }
        });

        imgbtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();

                mediaPlayer.release();

                imgbtnPlay.setImageResource(R.drawable.play64);

                KhoiTao();

                imgdisc.clearAnimation();
            }
        });

        imgbtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position++;
                if(position > mySongs.size() -1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }

                KhoiTao();

                imgdisc.startAnimation(animation);

                mediaPlayer.start();

                imgbtnPlay.setImageResource(R.drawable.pause64);
            }
        });

        imgbtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position--;
                if (position < 0) {
                    position = mySongs.size() - 1;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                KhoiTao();

                imgdisc.startAnimation(animation);

                mediaPlayer.start();
                imgbtnPlay.setImageResource(R.drawable.pause64);
            }
        });

        skbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skbarSong.getProgress());
            }
        });

    }

    private  void  AnhXa(){
        txtTitle        =   (TextView)  findViewById(R.id.txtTitle);
        txtTimeStart    =   (TextView)  findViewById(R.id.txtTimeStart);
        txtTimeEnd      =   (TextView)  findViewById(R.id.txtTimeEnd);

        skbarSong       =   (SeekBar)   findViewById(R.id.skbarSong);

        imgdisc         =   (ImageView) findViewById(R.id.imageViewDisc);

        imgbtnPrev      =   (ImageButton)findViewById(R.id.imgbtnPrev);
        imgbtnNext      =   (ImageButton)findViewById(R.id.imgbtnNext);
        imgbtnPlay      =   (ImageButton)findViewById(R.id.imgbtnPlay);
        imgbtnStop      =   (ImageButton)findViewById(R.id.imgbtnStop);
    }

    private void KhoiTao(){

        Uri u =Uri.parse(mySongs.get(position).toString());

        mediaPlayer =MediaPlayer.create(getApplicationContext(),u);

        txtTitle.setText(mySongs.get(position).getName().replace(".mp3","").replace(".flac","").toString());

        SetTimeEnd();
    }

    private  void SetTimeEnd(){
        SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");

        txtTimeEnd.setText(dinhdanggio.format(mediaPlayer.getDuration()));

        //gán max của skbarSong = mediaPlayer.getDuratiom()

        skbarSong.setMax(mediaPlayer.getDuration());
    }

    private  void CapNhatTimeSong() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");

                txtTimeStart.setText(dinhdanggio.format(mediaPlayer.getCurrentPosition()));

                //cập nhật progress skbarSong

                skbarSong.setProgress(mediaPlayer.getCurrentPosition());

                //kiểm tra thời gian bài hát -> nếu kết thúc -> next

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        position++;

                        if (position > mySongs.size() - 1) {

                            position = 0;

                        }

                        if (mediaPlayer.isPlaying()) {

                            mediaPlayer.stop();
                        }

                        KhoiTao();

                        txtTitle.setText(mySongs.get(position).getName().replace(".mp3","").replace(".flac","").toString());

                        mediaPlayer.start();

                        imgbtnPlay.setImageResource(R.drawable.pause64);

                        SetTimeEnd();

                        CapNhatTimeSong();

                    }
                });

                handler.postDelayed(this, 500);

            }
        }, 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem mn_dangnhap = menu.findItem(R.id.mndangnhap);
        mn_dangnhap.setVisible(false);

        MenuItem mn_exit = menu.findItem(R.id.mn_exit);
        mn_exit.setVisible(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.mn_exit:
                Intent back_frm = new Intent(PlayMusic.this, Music.class);
                startActivity(back_frm);
                overridePendingTransition(R.anim.slider_down,R.anim.slider_up);
        }
        return super.onOptionsItemSelected(item);
    }
}