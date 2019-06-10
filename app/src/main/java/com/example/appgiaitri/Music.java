package com.example.appgiaitri;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Music extends AppCompatActivity {

    String[] items;

    ListView lv_song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        //overridePendingTransition(R.anim.slider_down,R.anim.slider_up);

        lv_song = (ListView) findViewById(R.id.lv_song);

        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());

        items = new String[mySongs.size()];

        for (int i = 0; i < mySongs.size(); i++) {

            //toast(mySongs.get(i).getName().toString());
            items[i] = mySongs.get(i).getName().toString().replace(".mp3","").replace(".flac","");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.song_layout,R.id.textView,items);

        lv_song.setAdapter(adapter);

        lv_song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),PlayMusic.class).putExtra("pos",position).putExtra("songlist",mySongs));
            }
        });

    }

    public ArrayList<File> findSongs(File root) {

        ArrayList<File> al = new ArrayList<File>();

        File[] files = root.listFiles();

        for (File singleFiles : files) {
            if (singleFiles.isDirectory() && !singleFiles.isHidden()) {

                al.addAll(findSongs(singleFiles));
            } else {
                if (singleFiles.getName().endsWith(".mp3") || singleFiles.getName().endsWith(".flac")) {

                    al.add(singleFiles);
                }
            }
        }
        return al;
    }

    public void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}