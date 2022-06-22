package com.example.enduser.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.example.enduser.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        image = findViewById(R.id.image);
        image.animate().translationY((float) height/2).setDuration(2000);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent i = new Intent(getApplicationContext(),  SigninActivity.class);
                startActivity(i);
            }
        },5000);
    }
}