package com.annazou.metronome;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnScrollListener, NumberPicker.OnValueChangeListener, View.OnClickListener {

    private static final int MAX_SPEED = 240;
    private static final int MIN_SPEED = 30;

    ImageButton mStart;
    NumberPicker mSpeedPicker;

    int mSpeed;
    boolean mRunning;

    ImageView mDot;
    ObjectAnimator mDotAnimator;
    Timer mTimer;
    TimerTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpeedPicker = findViewById(R.id.speed);
        mStart = findViewById(R.id.start);
        mSpeedPicker.setMaxValue(MAX_SPEED);
        mSpeedPicker.setMinValue(MIN_SPEED);
        mSpeedPicker.setOnScrollListener(this);
        mSpeedPicker.setOnValueChangedListener(this);

        mDot = findViewById(R.id.dot);
        mDotAnimator = ObjectAnimator.ofFloat(mDot,
                "alpha", 1F, 0F);
        mDotAnimator.setDuration(150);

        mStart.setOnClickListener(this);
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mDotAnimator.start();
            }
        };
    }


    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        if(mSpeed != view.getValue() && mRunning){
            stop();
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    private void stop(){
        mTimer.cancel();
        mRunning = false;
        mDot.setVisibility(View.INVISIBLE);
        mStart.setImageResource(R.drawable.stop_button);
    }

    private void start(){
        mDot.setVisibility(View.VISIBLE);
        mSpeed = mSpeedPicker.getValue();
        mTimer.schedule(mTask, 0, (long)(1000 * 60 / (float)mSpeed));
        mRunning = true;
        mStart.setImageResource(R.drawable.start_button);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start){
            if(mRunning) {
                stop();
            }else{
                start();
            }
        }
    }
}
