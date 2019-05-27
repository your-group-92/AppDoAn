package com.example.appgiaitri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnmusic, btnaudio, btnabout, btnexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnmusic = (Button) findViewById(R.id.btnmusic);
        btnmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pagemusic = new Intent(MainActivity.this,Music.class);

                startActivity(pagemusic);
            }
        });

        btnaudio = (Button) findViewById(R.id.btnaudio);
        btnaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaudio = new Intent(MainActivity.this,list_audio.class);
                startActivity(listaudio);
            }
        });

        btnexit = (Button)findViewById(R.id.btnexit);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}
