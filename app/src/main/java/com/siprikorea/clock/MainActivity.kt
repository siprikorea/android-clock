package com.siprikorea.clock;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView mTimeView;
    private TextView mDateView;
    private SimpleDateFormat mTimeFormat;
    private SimpleDateFormat mDateFormat;
    static private Handler mTimeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeView = findViewById(R.id.time);
        mDateView = findViewById(R.id.date);
        mDateView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTimeView.getTextSize() / 2);

        mTimeFormat = new SimpleDateFormat("KK:mm:ss", Locale.getDefault());
        mDateFormat = new SimpleDateFormat("yyyy.MM.dd (E)", Locale.getDefault());

        mTimeHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Date date = new Date();
                mTimeView.setText(mTimeFormat.format(date));
                mDateView.setText(mDateFormat.format(date));
            }
        };

        runTime();
    }

    protected void runTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        mTimeHandler.sendMessage(mTimeHandler.obtainMessage());
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
