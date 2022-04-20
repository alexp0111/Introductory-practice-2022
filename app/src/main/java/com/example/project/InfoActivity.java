package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.prush.typedtextview.TypedTextView;

import static com.example.project.MainActivity.vlm;

public class InfoActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    private SoundPool sp;
    private int sound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //
        SharedPreferences preff = PreferenceManager.getDefaultSharedPreferences(this);
        TypedTextView typedTextView = findViewById(R.id.typedTextView);
        TextView t = findViewById(R.id.typedv2);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //
        if (preff.getBoolean("first", false) != true){
            TypedTextView.Builder builder = new TypedTextView.Builder(typedTextView)
                    .setTypingSpeed(50)
                    .splitSentences(true)
                    .setSentencePause(600)
                    .setCursorBlinkSpeed(1000)
                    .randomizeTypingSpeed(false)
                    .showCursor(true)
                    .playKeyStrokesAudio(false);

            typedTextView = builder.build();
            preff.edit().putBoolean("first", true).commit();
        } else {
            TypedTextView.Builder builder = new TypedTextView.Builder(typedTextView).playKeyStrokesAudio(false);
            typedTextView.setVisibility(View.GONE);
            t.setVisibility(View.VISIBLE);
        }
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        sound = sp.load(this, R.raw.click, 1);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}