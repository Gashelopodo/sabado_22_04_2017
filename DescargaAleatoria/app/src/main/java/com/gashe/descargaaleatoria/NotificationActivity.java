package com.gashe.descargaaleatoria;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent = getIntent();
        String message = intent.getStringExtra("MENSAJE");

        if(message == null) {

            Bitmap bitmap = intent.getParcelableExtra("FOTO");
            ImageView imageView = (ImageView) findViewById(R.id.myImage);
            imageView.setImageBitmap(bitmap);

        }else{

            TextView textView = (TextView) findViewById(R.id.myText);
            textView.setText(message);

        }

    }
}
