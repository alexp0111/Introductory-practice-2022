package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
// import android.database.SQLException; // DELETION
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

    // Practice addition - static analysis
    //
    //  В данном случае инструментом статического анализа будет выступать IDE Android studio
    //  Набор инструментов данной среды разработки является гибким и удобным в использовании
    //  Он позволяет не только выявить потенциальные ошибки, но и улучшить качество и читаемость
    //  кода уведомляя разработчика в случае, если та или иная переменная не используется или
    //  является фиктивной, а также помогая реорганизовывать булевые выражения и так далее.
    //
    // Practice addition - static analysis

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
    private long lastUpdate; // mls; DELETION
    private SensorEventListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        // Practice addition - static analysis
        //
        //  Среда разработки помечает данную конструкцию, как Legacy code, уведомляя разработчика
        //  о том, что данный класс и его методы более не разрабатываются и не обновляются,
        //  что может в дальнейшем привести к проблемам в взаимодействии компонентов системы.
        //
        //  Вследствие данного анализа следует ознакомится с актуальной документацией и найти
        //  альтернативу неподдерживаемому коду.
        //
        // Practice addition - static analysis
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        // Practice addition - dynamic analysis
        //  Как можно заметить данные из SharedPreferences сохраняются в объект класса
        //  SharedPreferences и в дальнейшем используются, подразумевая, что pref != null.
        //  Благодаря динамическому анализу удаётся получить соответствующую ошибку,
        //  при моделировании ситуации, когда файл был случайно удалён или утерян системой.
        //
        //  Вследствие данного анализа надлежит организовать проверку pref перед её использованием (строка 218)
        // Practice addition - dynamic analysis
        //
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        // Practice addition - static analysis
        //
        //  Среда разработки помечает строгую типизацию (EditText) серым цветом, тем самым
        //  уведомляя о необязательности данной конструкции на актуальный момент.
        //
        //  Вследствие данного анализа следует устранить лишний код, негативно влияющий
        //  на его стиль и читаемость.
        //
        // Practice addition - static analysis
        //
        dbHelper = new DBHelper(this);
        SQLdatabase = dbHelper.getWritableDatabase();
        // Practice addition - dynamic analysis
        //  Инициализированная база данных в последствие используется, подразумевая наличие в ней
        //  Указанных таблиц. Не проводится проверка на существовании базы данных, что приводит
        //  к ошибкам при попытке входа в аккаунт при помощи запоминаемых в БД данных.
        //
        //  Вследствие данного анализа надлежит организовать проверку SQLdatabase перед её использованием (строка 170)
        // Practice addition - dynamic analysis
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
                            // Practice addition - dynamic analysis
                            //  В процессе тестирования на эмуляторах разного поколения, была
                            //  обнаружена ошибка, не обрабатываемая программным кодом. Если в
                            //  смартфоне отсутствует вибромотор или существует проблема в
                            //  его определении, то объект класса Vibrator не инициализируется,
                            //  что приводит к ошибкам в процессе использования приложения.
                            //
                            //  Вследствие данного анализа надлежит организовать проверку
                            //  vibrator перед его использованием
                            // Practice addition - dynamic analysis
                            if (vibrator != null)
                                vibrator.vibrate(500);
                            if (SQLdatabase != null) { // ADDITION
                                Cursor cursor = SQLdatabase.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                                if (cursor.moveToFirst()) {
                                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                                    // Practice addition - static analysis
                                    //
                                    //  Среда разработки помечает переменную idIndex серым цветом
                                    //  уведомляя о том, что данная переменная нигде не используется
                                    //  и может быть устранена.
                                    //
                                    //  Вследствие данного анализа следует устранить лишний
                                    //  фиктивный код.
                                    //
                                    // Practice addition - static analysis
                                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
                                    int passIndex = cursor.getColumnIndex(DBHelper.KEY_PASS);
                                    mEmailField.setText(cursor.getString(emailIndex));
                                    mPasswordField.setText(cursor.getString(passIndex));
                                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

                                } else
                                    Log.d("mLog", "0 rows");
                                cursor.close();
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
        //
        nickname = (EditText) findViewById(R.id.field_nick);
        // Practice addition - static analysis
        //
        //  Ситуация аналогичная стр. 115.
        //
        // Practice addition - static analysis
        //
        // OLD CODE: if (pref.getBoolean("is_start", false) != true) {
        if (pref != null && pref.getBoolean("is_start", false) != true) {
            // Practice addition - static analysis
            //
            //  Среда разработки помечает констуркцию pref.getBoolean("is_start", false) != true
            //  желтым цветом, тем самым уведомляя разработчика о том, что утверждение
            //  может быть сокращено для его лучшей читаемости.
            //
            //  Вследствие данного анализа следует переписать выделенный фрагмент или
            //  воспользоваться инструментами среды для его автоматического изменения.
            //
            // Practice addition - static analysis
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
            // Practice addition - static analysis
            //
            //  (237, 249) Среда разработки помечает параметр объявления конструктора анонимного
            //  класса серым цветом и рекомендует заменить его на лямбду "->" [лямбда -
            //  упрощённая запись анонимного класса, реализующего функциональный интерфейс],
            //  тем самым повысив читаемость кода.
            //
            //  Вследствие данного анализа следует устранить лишний код, и заменить его
            //  в соответствии с требованиями системы.
            //
            // Practice addition - static analysis
            pref.edit().putBoolean("is_start", true).commit();
            // Practice addition - static analysis
            //
            //  (281) Среда разработки помечает конструкцию вызова метода на объекте класса
            //  SharedPreferences желтым цветом и, при наведении на метод, уведомляет
            //  разрабочика о том, что метод commit() имеет такой недостаток, как мнгновенная
            //  запись в базу данных, которое может встпуить в конфликт с логикой многопточности,
            //  вследствие чего надлежит использовать метод apply(), который обрабатывает запрос
            //  в фоновом режиме, тем самым предотвращая проблемы многопоточной записи.
            //
            //  Вследствие данного анализа следует заменить метод на более безопасный и устойчивый.
            //
            // Practice addition - static analysis
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
        // Practice addition - static analysis
        //
        //  (298 - 304) В данном блоке будут рассмотрены сразу две рекомендации инструмента
        //  статического анализа.
        //
        //  1. В строке 299 среда разработки предлагает заменить объявление ананоимного класса
        //  на лямбду.
        //
        //  2. В строке 302 нам сообщается о неиспользумеой переменной, однако
        //  очевидно, что в этом блоке допущеная куда более значительная ошибка. Метод
        //  onAuthStateChanged в данном случае отвечает за изменение значения перменной firebaseUser
        //  в случае изменения состояния mAuth, однако разработчиком была допущена ошибка и вместо
        //  переопределения значения переменной происходит объявления новой, нигде неиспользуемой.
        //  Данный кейс является отличной иллюстрацией уязвимости системы статического анализа,
        //  когда инструмент сообщает лишь неточности, стилистической ошибке
        //  в коде, при этом не учитывая, что логика метода в целом неправильная.
        //
        //  Вследствие данного анализа следует заменить объявление анонимного класса на лямбду,
        //  а также переписать логику метода onAuthStateChanged, учитывая, что его резулльтатом
        //  должно стать изменение значения перменной firebaseUser.
        //
        // Practice addition - static analysis
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
        // Practice addition - dynamic analysis
        //  В процессе тестирования приложения при обмене информации с базой данных
        //  FireBase было обнаружено, что, в ситуации, когда в базе данных отсутствуют поля
        //  на которые определяются ссылки в строках 334-340, данные объекты инициализируются
        //  значением null и вызывают ошибки при взаимодействии с приложением.
        //
        //
        //  Вследствие данного анализа надлежит организовать проверку
        //  определяемых ссылок на существование перед их использованием (480-586)
        // Practice addition - dynamic analysis
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
            // Practice addition - static analysis
            //
            //  Заменить анонимный класс лямбой.
            //
            // Practice addition - static analysis
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
                                // Practice addition - static analysis
                                //
                                //  Уже рассмотренная ситуация: Заменить анонимный класс лямбой.
                                //
                                // Practice addition - static analysis
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
        // ADDITIONS
        if (nicks != null) {
            nicks.child("counter").setValue(y + 1);
            nicks.child(Integer.toString((y + 1))).setValue(nickname.getText().toString());
        }
        // ADDITIONS
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
            // Practice addition - static analysis
            //
            //  Уже рассмотренная ситуация: Заменить анонимный класс лямбой.
            //
            // Practice addition - static analysis
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(LogInActivity.this, R.string.login, Toast.LENGTH_SHORT).show();
                        //
                        contentValues.put(DBHelper.KEY_EMAIL, email);
                        contentValues.put(DBHelper.KEY_PASS, password);
                        if (SQLdatabase != null)    // ADDITION
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
                            // Practice addition - static analysis
                            //
                            //  Среда разработки помечает объекты dataSnapshot и error
                            //  желтым цветом и представляет комментарий:
                            //  Not annotated parameter overrides @NonNull parameter
                            //  Проблема заключается в том, что метод onDataChange возвращает
                            //  своим результатом иной метод, в параметре которого прописана
                            //  аннотация @NonNull. В итоге возникает неточность, которая может
                            //  быть исправленая засчёт указания аннтоации в параметрах данного
                            //  метода.
                            //
                            //  Вследствие данного анализа провести аннотирование параметров
                            //  dataSnapShot и error.
                            //
                            //
                            // Practice addition - static analysis
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
                            // Practice addition - static analysis
                            //
                            //  Среда разработки помечает вызов методов на объекте dataSnapshot
                            //  сообщая разработчику, что данная распаковка информации из базы
                            //  данных может привести к ошибке NullPointerException.
                            //
                            //  Вследствие данного анализа обернуть потенциально опасный код в
                            //  конструкцию try catch или провести ряд проверко перед их вызовом,
                            //  чтобы не допустить ошибки работы приложения.
                            //
                            //
                            // Practice addition - static analysis
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                    if (champion1 != null)  //  ADDITION
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
                    if (champion2 != null)  //  ADDITION
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
                    if (champion3 != null)  //  ADDITION
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
                    if (champion4 != null)  //  ADDITION
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
                    if (champion5 != null)  //  ADDITION
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
        // Practice addition - static analysis
        //
        //  В данном случае среда разработки остлеживает все точки вызова метода validateForm
        //  и так как во всех случаях методы вызывается с отрицанием, следует изменить логику
        //  метода и возвращаемое значение, чтобы улучшить читаемость кода.
        //
        // Practice addition - static analysis
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

        // ADDITIONS
        if (sp == null) {
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            sp.setOnLoadCompleteListener(this);
            sound = sp.load(this, R.raw.click, 1);
        }
        // ADDITIONS

        sensorManager.registerListener(listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Practice addition - dynamic analysis
        //  Одним из ключевых видов ошибок, которые позволяет выделить динамический анализ,
        //  являются ошибки в работе с потоками. Хоть данный класс и не работает в режиме
        //  многопоточности явно, однако он использует такие классы, как FireBase и SoundPool,
        //  которые создают собственные потоки.
        //  Первый - является продуктом с высокой устойчивостью к ошибкам. Все его методы
        //  самостоятельно обрабатывают ошибки и исключения, тем самым снимая нагрузку с
        //  программиста. Однако второй класс является более сложным и именно он в результате
        //  тестирования привёл к обнаружению неочевидной ошибки.
        //
        //  Тест: Многократный процесс запуска \ останова activity.
        //
        //  Ошибка: Error creating AudioTrack.
        //
        //  Причина: Лимит в ОС Android по количеству загруженных треков равен 32, что приводит
        //  к ошибке в процессе многократного перезапуска приложения из-за того, что поток треков
        //  не очищается.
        //
        //  Решение: В методе жизненного цикла приложения onPause() следует проводить чистку
        //  на объекте класса SoundPool (строка 662)
        //
        //  PS: После добавления механики чистки SoundPool обязательно добавить инициализацию
        //  в методе жизненного цикла onResume() во избежание ошибки NullPointerException.
        //
        // Practice addition - dynamic analysis

        // ADDITIONS
        if (sp != null) {
            sp.release();
            sp = null;
        }
        // ADDITIONS
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
                            // Practice addition - static analysis
                            //
                            //  Среда разработки сообщает о возможном результате выполнения
                            //  dataSnapshot.child(Integer.toString(i)).getValue(String.class) -
                            //  null, что приведёт к ошибке в процессе выполнения приложения.
                            //
                            //  Вследствие анализа провести проверку перед исопльзованиям опасной
                            //  конструкции или изменить передаваемое в лог значение.
                            //
                            // Practice addition - static analysis
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
            if (sp != null)  //  ADDITION
                sp.play(sound, vlm, vlm, 0, 0, 1);
            // Practice addition - dynamic analysis
            //  В процессе тестирования аудиопотока путём одновременного нажатия множества
            //  кнопок, запускающих процесс воспроизведения "клика" было выявлено, что при нажатии
            //  кнопки назад, если успеть нажать иную кнопку, то будет обнаружена ошибка
            //  NullPointerException, так как перед переходом в другую активность в жизненном
            //  цикле приложения отрабатывает метод onPause() присваивающий переменной sp
            //  значение null во избежание переполнения лимита запускаемых треков.
            //
            // Вследствие обнаруженной проблемы стоит установить условие на значение sp отличное от
            // null перед вызовом методов на данном объекте (строки 749 - 783)
            // Practice addition - dynamic analysis
        } else if (v.getId() == R.id.email_sign_in_button) {
            //
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
            v.startAnimation(animation);
            if (sp != null)  //  ADDITION
                sp.play(sound, vlm, vlm, 0, 0, 1);
        } else if (v.getId() == R.id.go_out) {
            if (z == 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.nextback, R.anim.alpha);
                final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
                v.startAnimation(animation);
                if (sp != null)  //  ADDITION
                    sp.play(sound, vlm, vlm, 0, 0, 1);
            } else {
                Toast.makeText(LogInActivity.this, R.string.plsconemail, Toast.LENGTH_SHORT).show();
                final Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_go);
                v.startAnimation(animation);
                if (sp != null)  //  ADDITION
                    sp.play(sound, vlm, vlm, 0, 0, 1);
            }
            // Practice addition - static analysis
            //
            //  Среда разработки предлагает вынести повторяющейся код из условной конструкции,
            //  что позволит улучшить читаемость кода.
            //
            // Practice addition - static analysis
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }
}