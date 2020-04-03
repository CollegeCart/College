package com.example.collegecart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {



              startActivity(new Intent(splash.this , MainActivity.class));
              finish();



          }
      },1500);



    }
}
