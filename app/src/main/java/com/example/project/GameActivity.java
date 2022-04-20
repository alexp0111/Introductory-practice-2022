package com.example.project;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.project.MainActivity.vlm;

public class GameActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {
    private GameMain gameView = null;
    private Intent intent;
    private View v;
    private Animation animation;
    private SoundPool sp;
    private int sound;
    CountDownTimer countDownTimer = new CountDownTimer(TimeActivity.time * 1000 - 700, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }

        @Override
        public void onFinish() {
            startActivity(intent);
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            finish();
        }
    }.start();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //
        gameView = findViewById(R.id.gamemain);
        intent = new Intent(this, ItogActivity.class);
        animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        sound = sp.load(this, R.raw.click, 1);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}