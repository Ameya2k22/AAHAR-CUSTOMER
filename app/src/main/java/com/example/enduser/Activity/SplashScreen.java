package com.example.enduser.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.enduser.R;

public class SplashScreen extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        image = findViewById(R.id.image);
        image.animate().translationY(height/2).setDuration(2000);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
<<<<<<< HEAD
                Intent i = new Intent(getApplicationContext(),  SigninActivity.class);
                startActivity(i);
=======
                startActivity(new Intent(SplashScreen.this, SigninActivity.class));
>>>>>>> 2a2e65081d82a63386636c9bf89bb77254e9cc51
            }
        },5000);
    }
}