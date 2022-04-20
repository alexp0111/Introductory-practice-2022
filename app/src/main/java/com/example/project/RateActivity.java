package com.example.project;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.example.project.LogInActivity.fool;
import static com.example.project.LogInActivity.fool_count;
import static com.example.project.LogInActivity.mm;
import static com.example.project.LogInActivity.personemail;
import static com.example.project.LogInActivity.simple;
import static com.example.project.LogInActivity.simple_count;
import static com.example.project.MainActivity.vlm;

public class RateActivity extends BaseActivity implements SoundPool.OnLoadCompleteListener {

    public static LinearLayout l1, l2, l3, l4, l5, l6, l7;
    private Animation animation;
    private SoundPool sp;
    private int sound;
    private Bundle bundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //
        animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        sound = sp.load(this, R.raw.click, 1);
        //
        l1 = (LinearLayout) findViewById(R.id.ach_1);
        l2 = (LinearLayout) findViewById(R.id.ach_2);
        l3 = (LinearLayout) findViewById(R.id.ach_3);
        l4 = (LinearLayout) findViewById(R.id.ach_4);
        l5 = (LinearLayout) findViewById(R.id.ach_5);
        l6 = (LinearLayout) findViewById(R.id.ach_6);
        l7 = (LinearLayout) findViewById(R.id.ach_7);
        //
        if (mm >= 1) l1.setVisibility(View.VISIBLE);
        if (mm >= 10) l2.setVisibility(View.VISIBLE);
        if (mm >= 50) l3.setVisibility(View.VISIBLE);
        if (mm >= 100) l4.setVisibility(View.VISIBLE);
        if (mm >= 500) l5.setVisibility(View.VISIBLE);
        if (simple_count >= 5) personemail.child("achieve").child("simple").setValue(1);
        if (fool_count >= 5) personemail.child("achieve").child("fool").setValue(1);
        //
        if (fool == 1) l7.setVisibility(View.VISIBLE);
        if (simple == 1) l6.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }

    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            sp.play(sound, vlm, vlm, 0, 0, 1);
            v.startAnimation(animation);
            //
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                View v1 = (ImageView) findViewById(R.id.lamp);
                if (v1 != null) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RateActivity.this, v1, "lampochka");
                    bundle = options.toBundle();
                }
            }
            if (bundle == null) {
                startActivity(intent);
            } else {
                startActivity(intent, bundle);
                CountDownTimer cdt = new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        finish();
                    }
                }.start();
            }
        }
        if (v.getId() == R.id.nulll) {
            sp.play(sound, vlm, vlm, 0, 0, 1);
            v.startAnimation(animation);
            Toast.makeText(this, R.string.reset, Toast.LENGTH_SHORT).show();
            //
            personemail.child("statistic").child("itscr").setValue(0);
            personemail.child("statistic").child("allscr").setValue(0);
            personemail.child("statistic").child("mm").setValue(0);
            personemail.child("achieve").child("fool_count").setValue(0);
            personemail.child("achieve").child("simple_count").setValue(0);
            personemail.child("achieve").child("fool").setValue(0);
            personemail.child("achieve").child("simple").setValue(0);
            //
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);
            l3.setVisibility(View.GONE);
            l4.setVisibility(View.GONE);
            l5.setVisibility(View.GONE);
            l6.setVisibility(View.GONE);
            l7.setVisibility(View.GONE);
        }
    }
}