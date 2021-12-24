package com.tubes.workoutdailyplanner.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.tubes.workoutdailyplanner.R;


public class SplashScreen extends AppCompatActivity {

    //Varabel
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView text, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        forcePortraitOrientation();

        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        text = findViewById(R.id.txtlogo);
        text2 = findViewById(R.id.txtslogan);


        image.setAnimation(topAnim);
        text.setAnimation(topAnim);
        text2.setAnimation(bottomAnim);


        int splash_screen = 4000;
        new Handler().postDelayed(() -> {
            SplashScreen.this.startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            SplashScreen.this.finish();
        }, splash_screen);

    }

    @SuppressLint("WrongConstant")
    private void forcePortraitOrientation() {
        setRequestedOrientation(1);
    }
}
