package com.example.enduser.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.enduser.R;

public class SplashScreen extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        image = findViewById(R.id.image);
        image.animate().translationY(1200).setDuration(2000);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        },5000);
    }
}