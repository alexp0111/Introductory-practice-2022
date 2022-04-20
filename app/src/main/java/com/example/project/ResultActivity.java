package com.example.project;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prush.typedtextview.TypedTextView;

import static com.example.project.ItogActivity.d;
import static com.example.project.ItogActivity.t1;
import static com.example.project.ItogActivity.t10;
import static com.example.project.ItogActivity.t11;
import static com.example.project.ItogActivity.t12;
import static com.example.project.ItogActivity.t2;
import static com.example.project.ItogActivity.t3;
import static com.example.project.ItogActivity.t4;
import static com.example.project.ItogActivity.t5;
import static com.example.project.ItogActivity.t6;
import static com.example.project.ItogActivity.t7;
import static com.example.project.ItogActivity.t8;
import static com.example.project.ItogActivity.t9;
import static com.example.project.LogInActivity.fool_count;
import static com.example.project.LogInActivity.mm;
import static com.example.project.LogInActivity.personemail;
import static com.example.project.LogInActivity.simple_count;
import static com.example.project.MainActivity.type;
import static com.example.project.MainActivity.vlm;

public class ResultActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    private TypedTextView textView;
    private String str;
    private Integer F = 0;

    private SoundPool sp;
    private int sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //
        F = ((d * 100) / TimeActivity.time);
        if (F == 100) {
            str = getString(R.string.superrr);
        } else if ((F >= 80) && (F < 100)) {
            str = getString(R.string.good);
        } else if ((F >= 50) && (F < 80)) {
            str = getString(R.string.notbad);
        } else if ((F >= 20) && (F < 50)) {
            str = getString(R.string.ycdb);
        } else if ((F >= 0) && (F < 20)) {
            str = getString(R.string.dngu);
        }
        //
        textView = findViewById(R.id.resulttext);
        textView.playKeyStrokesAudio(type);
        textView.setTypedText(d + " / " + TimeActivity.time + "\n" + "\n" + str);
        //
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        sound = sp.load(this, R.raw.click, 1);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.home) {
            if ((mm == 1) || (mm == 10) || (mm == 50) || (mm == 100) || (mm == 500))
                Toast.makeText(this, R.string.nach, Toast.LENGTH_SHORT).show();
            //
            if (fool_count == 5) {
                personemail.child("achieve").child("fool_count").setValue(2020);
                Toast toast = Toast.makeText(this, R.string.fool, Toast.LENGTH_SHORT);
                toast.show();
            }
            if (simple_count == 5) {
                personemail.child("achieve").child("simple_count").setValue(2020);
                Toast toast = Toast.makeText(this, R.string.geniy, Toast.LENGTH_SHORT);
                toast.show();
            }
            //
            sp.play(sound, vlm, vlm, 0, 0, 1);
            //
            GameMain.type1 = 0;
            GameMain.type2 = 0;
            GameMain.type3 = 0;
            GameMain.type4 = 0;
            GameMain.type5 = 0;
            GameMain.type6 = 0;
            GameMain.type7 = 0;
            GameMain.type8 = 0;
            GameMain.type9 = 0;
            GameMain.type10 = 0;
            GameMain.type11 = 0;
            GameMain.type12 = 0;
            //
            t1 = 0;
            t2 = 0;
            t3 = 0;
            t4 = 0;
            t5 = 0;
            t6 = 0;
            t7 = 0;
            t8 = 0;
            t9 = 0;
            t10 = 0;
            t11 = 0;
            t12 = 0;
            //
            d = 0;
            F = 0;
            //
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.nextback, R.anim.alpha);
            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
            v.startAnimation(animation);
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}