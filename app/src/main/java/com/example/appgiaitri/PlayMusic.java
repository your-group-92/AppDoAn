package com.example.appgiaitri;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayMusic extends AppCompatActivity {

    static MediaPlayer mediaPlayer;

    ArrayList<File> mySongs;

    TextView txtTitle, txtTimeStart, txtTimeEnd;

    SeekBar skbarSong;

    ImageView imgdisc;

    ImageButton imgbtnPrev, imgbtnPlay, imgbtnStop, imgbtnNext;

    int position = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();

        Bundle b = i.getExtras();

        mySongs = (ArrayList) b.getParcelableArrayList("songlist");

        position = b.getInt("pos",0);

        AnhXa();

        KhoiTao();

        mediaPlayer.start();

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


        //SetTimeEnd();
    }

}
