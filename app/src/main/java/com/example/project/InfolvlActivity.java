package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.prush.typedtextview.TypedTextView;

import java.util.ArrayList;

import static com.example.project.MainActivity.vlm;

public class InfolvlActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    private SoundPool sp;
    private int sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infolvl);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        //
        SharedPreferences prefff = PreferenceManager.getDefaultSharedPreferences(this);
        //
        TypedTextView txt = findViewById(R.id.textinfa);
        TextView txxxt = findViewById(R.id.typedlvl);
        if (prefff.getBoolean("first?", false) != true){
            TypedTextView.Builder builder = new TypedTextView.Builder(txt)
                    .setTypingSpeed(50)
                    .splitSentences(true)
                    .setSentencePause(600)
                    .setCursorBlinkSpeed(1000)
                    .randomizeTypingSpeed(false)
                    .showCursor(true)
                    .playKeyStrokesAudio(false);

            txt = builder.build();
            prefff.edit().putBoolean("first?", true).commit();
        } else {
            TypedTextView.Builder builder = new TypedTextView.Builder(txt).playKeyStrokesAudio(false);
            txt.setVisibility(View.GONE);
            txxxt.setVisibility(View.VISIBLE);
        }
        //
        sound = sp.load(this, R.raw.click, 1);
        //
        BarChart bar = (BarChart) findViewById(R.id.graph);
        //
        ArrayList<BarEntry> e1 = new ArrayList<>();
        e1.add(new BarEntry(33f, 0));
        e1.add(new BarEntry(50f, 1));
        e1.add(new BarEntry(66f, 2));
        e1.add(new BarEntry(100f, 3));
        //
        BarDataSet bardataset = new BarDataSet(e1, getString(R.string.lvls));
        ArrayList<String> labels = new ArrayList<String>();
        //
        labels.add("lvl_1");
        labels.add("lvl_2");
        labels.add("lvl_3");
        labels.add("lvl_4");
        //
        BarData data = new BarData(labels, bardataset);
        bar.setData(data);
        bar.setDescription(getString(R.string.difff));
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        bar.animateY(2000);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            Intent intent = new Intent(this, LevelActivity.class);
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