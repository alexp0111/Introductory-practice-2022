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

public class LevelActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    public static int level;
    private Animation animation;

    private SoundPool sp;
    private int sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
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
        Intent intent = new Intent(this, TimeActivity.class);
        //
        if (v.getId() == R.id.back) {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            finish();
            overridePendingTransition(R.anim.nextback, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.infolvl) {
            Intent intent2 = new Intent(getApplicationContext(), InfolvlActivity.class);
            startActivity(intent2);
            finish();
            overridePendingTransition(R.anim.nextback, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.lvl_btn_1) {
            level = 1;
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.lvl_btn_2) {
            level = 2;
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.lvl_btn_3) {
            level = 3;
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.lvl_btn_4) {
            level = 4;
            startActivity(intent);
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