package com.example.appgiaitri;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

public class PlayMusic extends AppCompatActivity {

    static MediaPlayer mediaPlayer;
    ArrayList<File> mySongs;

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

        Uri u = Uri.parse(mySongs.get(position).toString());


        mediaPlayer = MediaPlayer.create(getApplicationContext(),u);

        mediaPlayer.start();

    }
}
