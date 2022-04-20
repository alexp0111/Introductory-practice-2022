package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

import static com.example.project.MainActivity.vlm;

public class LogInActivity extends BaseActivity implements SoundPool.OnLoadCompleteListener {

    public static int marker = 0, mm = 0;
    public static String name = " ", ch1_nick = " ", ch2_nick = " ", ch3_nick = " ", ch4_nick = " ", ch5_nick = " ", id = " ";
    public static Integer itscore = 0, allscore = 0, fool = 0, simple = 0, fool_count = 0, simple_count = 0, ch_score = 0;
    public static Integer ch1_scr = 0, ch2_scr = 0, ch3_scr = 0, ch4_scr = 0, ch5_scr = 0;
    public static String id1 = " ", id2 = " ", id3 = " ", id4 = " ", id5 = " ";
    public static DatabaseReference personemail, champion1, champion2, champion3, champion4, champion5, nicks;
    //
    public static FirebaseDatabase database;
    SQLiteDatabase SQLdatabase;
    ContentValues contentValues;
    //
    //
    DBHelper dbHelper;
    //
    private SoundPool sp;
    private int sound, z = 0;
    private Double L = 0.0;
    private Integer y = 0;
    private FirebaseUser firebaseUser = null;
    private EditText mEmailField, mPasswordField, nickname;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //
    private SensorManager sensorManager;
    private boolean bul = false;
    private long lastUpdate, mls;
    private SensorEventListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        //
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        //
        dbHelper = new DBHelper(this);
        SQLdatabase = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();
        //
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    float[] values = event.values;
                    float x = values[0];
                    float y = values[1];
                    float z = values[2];
                    float accelationSquareRoot = (x * x + y * y + z * z)
                            / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
                    long actualTime = System.currentTimeMillis();

                    if (accelationSquareRoot >= 3.4)//Если тряска сильная
                    {
                        if (actualTime - lastUpdate < 250) {
                            return;
                        }
                        lastUpdate = actualTime;
                        if (bul) {
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(500);
                            try {
                                Cursor cursor = SQLdatabase.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                                if (cursor.moveToFirst()) {
                                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
                                    int passIndex = cursor.getColumnIndex(DBHelper.KEY_PASS);
                                    mEmailField.setText(cursor.getString(emailIndex));
                                    mPasswordField.setText(cursor.getString(passIndex));
                                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

                                } else
                                    Log.d("mLog", "0 rows");
                                cursor.close();
                            } catch (SQLException e) {
                                Toast.makeText(LogInActivity.this, R.string.ifnotlogin, Toast.LENGTH_SHORT).show();
                            }
                        }
                        bul = !bul;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();
        lastUpdate = System.currentTimeMillis();
        //
        nickname = (EditText) findViewById(R.id.field_nick);
        //
        if (pref.getBoolean("is_start", false) != true) {
            new GuideView.Builder(this)
                    .setTitle(getString(R.string.st1))
                    .setContentText(getString(R.string.st1_txt))
                    .setGravity(Gravity.center)
                    .setDismissType(DismissType.anywhere)
                    .setTargetView(nickname)
                    .setContentTextSize(12)
                    .setTitleTextSize(14)
                    .setGuideListener(new GuideListener() {
                        @Override
                        public void onDismiss(View view) {
                            new GuideView.Builder(LogInActivity.this)
                                    .setTitle(getString(R.string.st2))
                                    .setContentText(getString(R.string.st2_txt))
                                    .setGravity(Gravity.center)
                                    .setDismissType(DismissType.anywhere)
                                    .setTargetView(mEmailField)
                                    .setContentTextSize(12)
                                    .setTitleTextSize(14)
                                    .setGuideListener(new GuideListener() {
                                        @Override
                                        public void onDismiss(View view) {
                                            new GuideView.Builder(LogInActivity.this)
                                                    .setTitle(getString(R.string.st3))
                                                    .setContentText(getString(R.string.st3_txt))
                                                    .setGravity(Gravity.center)
                                                    .setDismissType(DismissType.anywhere)
                                                    .setTargetView(mPasswordField)
                                                    .setContentTextSize(12)
                                                    .setTitleTextSize(14)
                                                    .build()
                                                    .show();

                                        }
                                    })
                                    .build()
                                    .show();
                        }
                    })
                    .build()
                    .show();
            pref.edit().putBoolean("is_start", true).commit();
        } else {
            Toast.makeText(LogInActivity.this, R.string.shake, Toast.LENGTH_SHORT).show();
        }
        //
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
        //
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        sound = sp.load(this, R.raw.click, 1);
        //
        database = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        nicks = database.getReference("nicknames");
        //
        champion1 = database.getReference("champ1");
        champion2 = database.getReference("champ2");
        champion3 = database.getReference("champ3");
        champion4 = database.getReference("champ4");
        champion5 = database.getReference("champ5");
        //
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    ///////////////////////////
    //                       //
    //   Создание аккаунта   //
    //                       //
    ///////////////////////////

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", "oops");
                    Toast.makeText(LogInActivity.this, R.string.madeacc, Toast.LENGTH_SHORT).show();
                    //
                    personemail = database.getReference(firebaseUser.getUid());
                    //
                    personemail.child("statistic").child("itscr").setValue(0);
                    personemail.child("statistic").child("allscr").setValue(0);
                    personemail.child("statistic").child("mm").setValue(0);
                    //
                    personemail.child("nick").setValue(nickname.getText().toString());
                    personemail.child("championscore").setValue(0);
                    //
                    personemail.child("achieve").child("fool").setValue(0);
                    personemail.child("achieve").child("fool_count").setValue(0);
                    personemail.child("achieve").child("simple").setValue(0);
                    personemail.child("achieve").child("simple_count").setValue(0);
                    //
                    //
                    firebaseUser.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        z = 1;
                                        Toast.makeText(LogInActivity.this, R.string.email, Toast.LENGTH_SHORT).show();
                                        Log.d("TAG", "Email sent!");
                                    }
                                }
                            });
                    //
                } else {
                    Toast.makeText(LogInActivity.this, R.string.fail, Toast.LENGTH_SHORT).show();
                    marker = 0;
                }
                hideProgressDialog();
            }
        });
        nicks.child("counter").setValue(y + 1);
        nicks.child(Integer.toString((y + 1))).setValue(nickname.getText().toString());
    }

    ////////////////////////
    //                    //
    //   Вход в аккаунт   //
    //                    //
    ////////////////////////

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(LogInActivity.this, R.string.login, Toast.LENGTH_SHORT).show();
                        //
                        contentValues.put(DBHelper.KEY_EMAIL, email);
                        contentValues.put(DBHelper.KEY_PASS, password);
                        SQLdatabase.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                        //
                        z = 0;
                    } else {
                        Toast.makeText(LogInActivity.this, R.string.misna, Toast.LENGTH_SHORT).show();
                        z = 1;
                    }
                    //
                    id = firebaseUser.getUid();
                    if (id.equals(" ")) {
                        Toast.makeText(LogInActivity.this, "Error 404", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "disconnect");
                    } else {
                        marker = 1;
                    }
                    personemail = database.getReference(firebaseUser.getUid());
                    personemail.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            nickname.setText(dataSnapshot.child("nick").getValue(String.class));
                            //
                            //Users data
                            //
                            name = dataSnapshot.child("nick").getValue(String.class);
                            ch_score = dataSnapshot.child("championscore").getValue(Integer.class);
                            itscore = dataSnapshot.child("statistic").child("itscr").getValue(Integer.class);
                            allscore = dataSnapshot.child("statistic").child("allscr").getValue(Integer.class);
                            mm = dataSnapshot.child("statistic").child("mm").getValue(Integer.class);
                            fool = dataSnapshot.child("achieve").child("fool").getValue(Integer.class);
                            fool_count = dataSnapshot.child("achieve").child("fool_count").getValue(Integer.class);
                            simple = dataSnapshot.child("achieve").child("simple").getValue(Integer.class);
                            simple_count = dataSnapshot.child("achieve").child("simple_count").getValue(Integer.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                    champion1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ch1_nick = dataSnapshot.child("maiil").getValue(String.class);
                            id1 = dataSnapshot.child("id").getValue(String.class);
                            ch1_scr = dataSnapshot.child("scorr").getValue(Integer.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    champion2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ch2_nick = dataSnapshot.child("maiil").getValue(String.class);
                            id2 = dataSnapshot.child("id").getValue(String.class);
                            ch2_scr = dataSnapshot.child("scorr").getValue(Integer.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    champion3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ch3_nick = dataSnapshot.child("maiil").getValue(String.class);
                            id3 = dataSnapshot.child("id").getValue(String.class);
                            ch3_scr = dataSnapshot.child("scorr").getValue(Integer.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    champion4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ch4_nick = dataSnapshot.child("maiil").getValue(String.class);
                            id4 = dataSnapshot.child("id").getValue(String.class);
                            ch4_scr = dataSnapshot.child("scorr").getValue(Integer.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    champion5.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ch5_nick = dataSnapshot.child("maiil").getValue(String.class);
                            id5 = dataSnapshot.child("id").getValue(String.class);
                            ch5_scr = dataSnapshot.child("scorr").getValue(Integer.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(LogInActivity.this, R.string.fail, Toast.LENGTH_SHORT).show();
                    marker = 0;
                }
                hideProgressDialog();
            }
        });
    }

    /////////////////////////////////////////////////////
    //                                                 //
    //    Проверка на корректность введённых данных    //
    //                                                 //
    /////////////////////////////////////////////////////

    private boolean validateForm() {
        boolean valid = true;
        String email = mEmailField.getText().toString();
        //
        if (email.length() < 9) {
            Toast.makeText(LogInActivity.this, R.string.email_fail, Toast.LENGTH_SHORT).show();
            marker = 0;
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        //
        String password = mPasswordField.getText().toString();
        if (password.length() < 6) {
            Toast.makeText(LogInActivity.this, R.string.password_short, Toast.LENGTH_SHORT).show();
            marker = 0;
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        //
        return valid;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    // OnClick

    public void onClick(View v) {
        if (v.getId() == R.id.email_create_account_button) {
            //
            nicks.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("TAG", "come to");
                    y = dataSnapshot.child("counter").getValue(Integer.class);
                    Log.d("TAG", "start");
                    for (int i = 1; i <= y; i++) {
                        if ((nickname.getText().toString()).equals(dataSnapshot.child(Integer.toString(i)).getValue(String.class))) {
                            Log.d("TAG", dataSnapshot.child(Integer.toString(i)).getValue(String.class));
                            Log.d("TAG", nickname.getText().toString());
                            L = 2020.0;
                            break;
                        } else {
                            L = 0.0;
                        }
                    }
                    if (L > 0.0) {
                        Toast.makeText(LogInActivity.this, "Имя занято! \nВведите другой никнейм!", Toast.LENGTH_SHORT).show();
                    } else if (L == 0) {
                        createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                    }
                    Log.d("TAG", "checked");
                    Log.d("TAG", Double.toString(L));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //
            name = nickname.getText().toString();
            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        } else if (v.getId() == R.id.email_sign_in_button) {
            //
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
            v.startAnimation(animation);
            sp.play(sound, vlm, vlm, 0, 0, 1);
        } else if (v.getId() == R.id.go_out) {
            if (z == 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.nextback, R.anim.alpha);
                final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
                v.startAnimation(animation);
                sp.play(sound, vlm, vlm, 0, 0, 1);
            } else {
                Toast.makeText(LogInActivity.this, R.string.plsconemail, Toast.LENGTH_SHORT).show();
                final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
                v.startAnimation(animation);
                sp.play(sound, vlm, vlm, 0, 0, 1);
            }
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}