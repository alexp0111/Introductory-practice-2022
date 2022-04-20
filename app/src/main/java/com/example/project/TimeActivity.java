package com.example.project;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.project.MainActivity.vlm;

public class TimeActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    public static int time;
    private Animation animation;

    private SoundPool sp;
    private int sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //
        animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        sound = sp.load(this, R.raw.click, 1);
    }


    public void onClick(View v) {
        Intent intent1 = new Intent(this, GameActivity.class);
        //
        if (v.getId() == R.id.back) {
            Intent intent = new Intent(this, LevelActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.nextback, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.btn_5sec) {
            time = 5;
            startActivity(intent1);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.btn_10sec) {
            time = 10;
            startActivity(intent1);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.btn_15sec) {
            time = 15;
            startActivity(intent1);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.btn_20sec) {
            time = 20;
            startActivity(intent1);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}