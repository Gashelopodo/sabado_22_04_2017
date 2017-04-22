package com.gashe.despertarvideo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reproduzco un video
        VideoView videoView = (VideoView) findViewById(R.id.myVideo);
        String path = "android.resource://"+getPackageName()+"/"+R.raw.videobd;
        Uri urivideo = Uri.parse(path);
        videoView.setVideoURI(urivideo);
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();

    }
}
