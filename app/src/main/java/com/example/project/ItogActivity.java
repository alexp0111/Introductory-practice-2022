package com.example.project;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.project.GameMain.type1;
import static com.example.project.GameMain.type10;
import static com.example.project.GameMain.type11;
import static com.example.project.GameMain.type12;
import static com.example.project.GameMain.type2;
import static com.example.project.GameMain.type3;
import static com.example.project.GameMain.type5;
import static com.example.project.GameMain.type6;
import static com.example.project.GameMain.type7;
import static com.example.project.GameMain.type8;
import static com.example.project.GameMain.type9;
import static com.example.project.LogInActivity.allscore;
import static com.example.project.LogInActivity.ch_score;
import static com.example.project.LogInActivity.fool_count;
import static com.example.project.LogInActivity.itscore;
import static com.example.project.LogInActivity.mm;
import static com.example.project.LogInActivity.personemail;
import static com.example.project.LogInActivity.simple_count;
import static com.example.project.MainActivity.vlm;
import static com.example.project.TimeActivity.time;

public class ItogActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    public static int t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0, t9 = 0, t10 = 0, t11 = 0, t12 = 0;
    public static int d = 0;
    private SoundPool sp;
    private int sound;
    private Spinner text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11, text12;
    private Integer[] count = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itog);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.spin_it, count);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        //
        text1 = (Spinner) findViewById(R.id.Etxt1);
        text2 = (Spinner) findViewById(R.id.Etxt2);
        text3 = (Spinner) findViewById(R.id.Etxt3);
        text4 = (Spinner) findViewById(R.id.Etxt4);
        text5 = (Spinner) findViewById(R.id.Etxt5);
        text6 = (Spinner) findViewById(R.id.Etxt6);
        text7 = (Spinner) findViewById(R.id.Etxt7);
        text8 = (Spinner) findViewById(R.id.Etxt8);
        text9 = (Spinner) findViewById(R.id.Etxt9);
        text10 = (Spinner) findViewById(R.id.Etxt10);
        text11 = (Spinner) findViewById(R.id.Etxt11);
        text12 = (Spinner) findViewById(R.id.Etxt12);
        //
        text1.setAdapter(adapter);
        text2.setAdapter(adapter);
        text3.setAdapter(adapter);
        text4.setAdapter(adapter);
        text5.setAdapter(adapter);
        text6.setAdapter(adapter);
        text7.setAdapter(adapter);
        text8.setAdapter(adapter);
        text9.setAdapter(adapter);
        text10.setAdapter(adapter);
        text11.setAdapter(adapter);
        text12.setAdapter(adapter);
        //
        text1.setDropDownWidth(250);
        text2.setDropDownWidth(250);
        text3.setDropDownWidth(250);
        text4.setDropDownWidth(250);
        text5.setDropDownWidth(250);
        text6.setDropDownWidth(250);
        text7.setDropDownWidth(250);
        text8.setDropDownWidth(250);
        text9.setDropDownWidth(250);
        text10.setDropDownWidth(250);
        text11.setDropDownWidth(250);
        text12.setDropDownWidth(250);
        text1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t4 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t5 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t6 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t7 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t8 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t9 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t10 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t11 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        text12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                t12 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        sound = sp.load(this, R.raw.click, 1);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
            overridePendingTransition(R.anim.nextback, R.anim.alpha);
            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        }
        if (v.getId() == R.id.check) {
            //
            sp.play(sound, vlm, vlm, 0, 0, 1);
            //
            try {
                if (LevelActivity.level == 1) {
                    if (type1 >= t1) {
                        d += t1;
                    }
                    if (type3 >= t3) {
                        d += t3;
                    }
                    if (type5 >= t5) {
                        d += t5;
                    }
                    if (type7 >= t7) {
                        d += t7;
                    }
                }
                if (LevelActivity.level == 2) {
                    if (type1 >= t1) {
                        d += t1;
                    }
                    if (type3 >= t3) {
                        d += t3;
                    }
                    if (type5 >= t5) {
                        d += t5;
                    }
                    if (type7 >= t7) {
                        d += t7;
                    }
                    if (type9 >= t9) {
                        d += t9;
                    }
                    if (type11 >= t11) {
                        d += t11;
                    }
                }
                if (LevelActivity.level == 3) {
                    if (type1 >= t1) {
                        d += t1;
                    }
                    if (type2 >= t2) {
                        d += t2;
                    }
                    if (type3 >= t3) {
                        d += t3;
                    }
                    if (type5 >= t5) {
                        d += t5;
                    }
                    if (type6 >= t6) {
                        d += t6;
                    }
                    if (type7 >= t7) {
                        d += t7;
                    }
                    if (type9 >= t9) {
                        d += t9;
                    }
                    if (type10 >= t10) {
                        d += t10;
                    }
                    if (type11 >= t11) {
                        d += t11;
                    }
                }
                if (LevelActivity.level == 4) {
                    if (type1 >= t1) {
                        d += t1;
                    }
                    if (type2 >= t2) {
                        d += t2;
                    }
                    if (type3 >= t3) {
                        d += t3;
                    }
                    if (type3 >= t3) {
                        d += t4;
                    }
                    if (type5 >= t5) {
                        d += t5;
                    }
                    if (type6 >= t6) {
                        d += t6;
                    }
                    if (type7 >= t7) {
                        d += t7;
                    }
                    if (type8 >= t8) {
                        d += t8;
                    }
                    if (type9 >= t9) {
                        d += t9;
                    }
                    if (type10 >= t10) {
                        d += t10;
                    }
                    if (type11 >= t11) {
                        d += t11;
                    }
                    if (type12 >= t12) {
                        d += t12;
                    }
                }
                //
                Intent intent = new Intent(this, ResultActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.prev, R.anim.alpha);
                final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
                v.startAnimation(animation);

            } catch (Exception e) {
                Toast.makeText(ItogActivity.this, R.string.dei, Toast.LENGTH_SHORT).show();
            }
            //
            try {
                personemail.child("statistic").child("itscr").setValue(itscore + d);
                personemail.child("statistic").child("allscr").setValue(allscore + time);
                personemail.child("statistic").child("mm").setValue(mm + 1);
                //
                if (d / time == 1) {
                    personemail.child("achieve").child("simple_count").setValue(simple_count + 1);
                    personemail.child("championscore").setValue(ch_score + 1);
                } else if ((d / time != 1) && (simple_count < 5))
                    personemail.child("achieve").child("simple_count").setValue(0);
                //
                if ((d * 100) / time == 0) {
                    personemail.child("achieve").child("fool_count").setValue(fool_count + 1);
                } else if (((d * 100) / time != 0) && (fool_count < 5))
                    personemail.child("achieve").child("fool_count").setValue(0);
                //
            } catch (Exception e) {
                Toast.makeText(ItogActivity.this, R.string.Log_to_play, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}