package com.example.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class GameMain extends View {
    public static int type1 = 0, type2 = 0, type3 = 0, type4 = 0, type5 = 0, type6 = 0, type7 = 0, type8 = 0, type9 = 0, type10 = 0, type11 = 0, type12 = 0;
    private int width = 0, height = 0, Vr = TimeActivity.time * 1000 - 700, collvl = 0, figlvl = 0;//Vr - время работы таймера, где -700 это уменьшение времени между показом последней фигуры и преходом
    private float x, y, color1, figure1;
    private long RS = 0; //Счётчик показа фигуры
    private Random rnd;

    //
    public GameMain(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        rnd = new Random(System.currentTimeMillis());
        DisplayMetrics metrics = new DisplayMetrics();
        ((GameActivity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        //
        final CountDownTimer countDownTimer = new CountDownTimer(Vr, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                invalidate();
                RS = 1000;
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint1 = new Paint();
        if (RS % 1000 == 0) {
            //Задаём координаты
            x = (float) (Math.random() * width);
            y = (float) (Math.random() * height);
            //
            if (LevelActivity.level == 1) {
                collvl = 2;
                figlvl = 2;
            }
            if (LevelActivity.level == 2) {
                collvl = 2;
                figlvl = 3;
            }
            if (LevelActivity.level == 3) {
                collvl = 3;
                figlvl = 3;
            }
            if (LevelActivity.level == 4) {
                collvl = 4;
                figlvl = 3;
            }
            //
            color1 = 1 + rnd.nextInt(collvl);
            figure1 = 1 + rnd.nextInt(figlvl);
            //Устанавливаем цвет фигуры
            if (color1 == 1) {
                paint1.setColor(Color.argb(255, 0, 162, 232));
            } else if (color1 == 2) {
                paint1.setColor(Color.argb(255, 19, 196, 15));
            } else if (color1 == 3) {
                paint1.setColor(Color.argb(255, 237, 28, 36));
            } else if (color1 == 4) {
                paint1.setColor(Color.argb(255, 255, 242, 0));
            }

            //////////////////////////////////////////////////////////////////
            //                                                              //
            //          Фигура рисуется в квадрате со стороной 300          //
            //                                                              //
            //////////////////////////////////////////////////////////////////

            //Рисуем выбранную фигуру
            if (figure1 == 1) {
                if ((x + 300) >= width) x = width - 300;
                if (y >= height) y = height;
                if (y <= 300) y = 300;
                Path path = new Path();
                path.moveTo(x, y);
                path.lineTo((int) (x + 150), (int) (y - 300));
                path.lineTo((int) (x + 300), (int) y);
                path.lineTo((int) x, (int) y);
                path.close();
                //
                if (color1 == 1) {
                    type3++;
                } else if (color1 == 2) {
                    type1++;
                } else if (color1 == 3) {
                    type2++;
                } else if (color1 == 4) {
                    type4++;
                }
                canvas.drawPath(path, paint1);
            } else if (figure1 == 2) {
                if ((x + 300) >= width) x = width - 300;
                if ((y + 300) >= height) y = height - 300;
                canvas.drawRect(x, y, x + 300, y + 300, paint1);
                if (color1 == 1) {
                    type7++;
                } else if (color1 == 2) {
                    type5++;
                } else if (color1 == 3) {
                    type6++;
                } else if (color1 == 4) {
                    type8++;
                }
            } else if (figure1 == 3) {
                if ((x + 150) >= width) x = width - 150;
                if (x <= 150) x = 150;
                if ((y + 150) >= height) y = height - 150;
                if (y <= height) y = 150;
                canvas.drawCircle(x, y, 150, paint1);
                if (color1 == 1) {
                    type11++;
                } else if (color1 == 2) {
                    type9++;
                } else if (color1 == 3) {
                    type10++;
                } else if (color1 == 4) {
                    type12++;
                }
            }
        }
    }
}