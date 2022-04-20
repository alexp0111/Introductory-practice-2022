package com.example.project;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.project.LogInActivity.ch1_nick;
import static com.example.project.LogInActivity.ch1_scr;
import static com.example.project.LogInActivity.ch2_nick;
import static com.example.project.LogInActivity.ch2_scr;
import static com.example.project.LogInActivity.ch3_nick;
import static com.example.project.LogInActivity.ch3_scr;
import static com.example.project.LogInActivity.ch4_nick;
import static com.example.project.LogInActivity.ch4_scr;
import static com.example.project.LogInActivity.ch5_nick;
import static com.example.project.LogInActivity.ch5_scr;
import static com.example.project.LogInActivity.ch_score;
import static com.example.project.LogInActivity.champion1;
import static com.example.project.LogInActivity.champion2;
import static com.example.project.LogInActivity.champion3;
import static com.example.project.LogInActivity.champion4;
import static com.example.project.LogInActivity.champion5;
import static com.example.project.LogInActivity.id;
import static com.example.project.LogInActivity.id1;
import static com.example.project.LogInActivity.id2;
import static com.example.project.LogInActivity.id3;
import static com.example.project.LogInActivity.id4;
import static com.example.project.LogInActivity.id5;
import static com.example.project.LogInActivity.marker;
import static com.example.project.LogInActivity.name;

///////////////////////////////////////////////////////////
//                                                       //
//  Проектная работа Петровского Александра Андреевича!  //
//                                                       //
///////////////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    public static int vlm = 1;
    public static boolean type = true;
    private Animation animation;
    private SoundPool sp;
    private int sound, $;
    private double rating;
    private Bundle bundle = null;
    private Dialog dialog;
    private TextView ch1, ch2, ch3, ch4, ch5;
    CountDownTimer countDownTimer = new CountDownTimer(Integer.MAX_VALUE, 1) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (LogInActivity.marker == 1) {
                findViewById(R.id.authorization1).setVisibility(View.GONE);
                findViewById(R.id.rate_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.pedestal).setVisibility(View.VISIBLE);
                TextView textView = (TextView) findViewById(R.id.texthi);
                Button btn1 = (Button) findViewById(R.id.rate);
                textView.setText("Hi, " + LogInActivity.name);
                textView.setBackgroundResource(R.drawable.txtfon);
                //
                try {
                    rating = (LogInActivity.itscore * 100) / LogInActivity.allscore;
                } catch (Exception e) {
                    rating = 0.0;
                }
                //
                btn1.setText(getString(R.string.your_rate) + " " + rating + " %");
                btn1.setBackgroundResource(R.drawable.ratestyle);
                //
                ch1.setText(ch1_nick);
                ch2.setText(ch2_nick);
                ch3.setText(ch3_nick);
                ch4.setText(ch4_nick);
                ch5.setText(ch5_nick);
                //
            } else if (LogInActivity.marker == 0) {
                findViewById(R.id.authorization1).setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFinish() {
        }
    }.start();
    private List<Champion> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_view);
        //
        ch1 = (TextView) dialog.findViewById(R.id.ch1);
        ch2 = (TextView) dialog.findViewById(R.id.ch2);
        ch3 = (TextView) dialog.findViewById(R.id.ch3);
        ch4 = (TextView) dialog.findViewById(R.id.ch4);
        ch5 = (TextView) dialog.findViewById(R.id.ch5);
        //
        Champion per1 = new Champion(ch1_scr, id1, ch1_nick);
        Champion per2 = new Champion(ch2_scr, id2, ch2_nick);
        Champion per3 = new Champion(ch3_scr, id3, ch3_nick);
        Champion per4 = new Champion(ch4_scr, id4, ch4_nick);
        Champion per5 = new Champion(ch5_scr, id5, ch5_nick);
        Champion person = new Champion(ch_score, id, name);
        //
        //
        list = new ArrayList<Champion>();
        //
        list.add(per1);
        list.add(per2);
        list.add(per3);
        list.add(per4);
        list.add(per5);
        for (int i = 0; i < 5; i++) {
            if (list.get(i).getId().equals(id)) {
                list.set(i, person);
                $ = 100;
                break;
            }
        }
        if ($ == 100) {
            Collections.sort(list);
        } else if ($ == 0) {
            list.add(person);
            Collections.sort(list);
            list.remove(0);
        }
        //
        if ((marker == 1) && (!per1.getId().equals(" ")) && (!per2.getId().equals(" ")) && (!per3.getId().equals(" ")) && (!per4.getId().equals(" ")) && (!per5.getId().equals(" "))) {
            champion1.child("id").setValue(list.get(4).getId());
            champion1.child("maiil").setValue(list.get(4).getNick());
            champion1.child("scorr").setValue(list.get(4).getScore());
            //
            champion2.child("id").setValue(list.get(3).getId());
            champion2.child("maiil").setValue(list.get(3).getNick());
            champion2.child("scorr").setValue(list.get(3).getScore());
            //
            champion3.child("id").setValue(list.get(2).getId());
            champion3.child("maiil").setValue(list.get(2).getNick());
            champion3.child("scorr").setValue(list.get(2).getScore());
            //
            champion4.child("id").setValue(list.get(1).getId());
            champion4.child("maiil").setValue(list.get(1).getNick());
            champion4.child("scorr").setValue(list.get(1).getScore());
            //
            champion5.child("id").setValue(list.get(0).getId());
            champion5.child("maiil").setValue(list.get(0).getNick());
            champion5.child("scorr").setValue(list.get(0).getScore());
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.mute) {
            v.startAnimation(animation);
            if (vlm == 0) {
                Toast.makeText(this, R.string.soundson, Toast.LENGTH_LONG).show();
                vlm = 1;
                type = true;
                sp.play(sound, 1, 1, 0, 0, 1);
            } else if (vlm == 1) {
                vlm = 0;
                type = false;
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                Toast.makeText(this, R.string.soundsoff, Toast.LENGTH_LONG).show();
            }
        }
        if (v.getId() == R.id.info) {
            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.nextback, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.start) {
            Intent intent = new Intent(getApplicationContext(), LevelActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.pedestal) {
            //
            dialog.show();
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.endmainactivity) {
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        if (v.getId() == R.id.authorization1) {
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
            Intent intent2 = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.prev, R.anim.alpha);
            finish();
        }
        if (v.getId() == R.id.rate) {
            Intent intent = new Intent(getApplicationContext(), RateActivity.class);
            sp.play(sound, vlm, vlm, 0, 0, 1);
            v.startAnimation(animation);
            //
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                View v1 = (ImageView) findViewById(R.id.lamp);
                if (v1 != null) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, v1, "lampochka");
                    bundle = options.toBundle();
                }
            }
            if (bundle == null) {
                startActivity(intent);
            } else {
                startActivity(intent, bundle);
                CountDownTimer count = new CountDownTimer(2000, 1000) {
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
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}