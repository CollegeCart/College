package com.example.collegecart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class fullimage extends AppCompatActivity {

    ImageView fullimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);

        fullimage = findViewById(R.id.fullimage);
        final Intent intent= getIntent();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(fullimage.this).load(intent.getStringExtra("Image")).into(fullimage);
            }
        },500);


    }
}
