package com.siprikorea.clock;

import android.content.Context;
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
    private Context mContext;
    private TextView mTimeView;
    private TextView mDateView;
    static private Handler mTimeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mTimeView = findViewById(R.id.time);
        mDateView = findViewById(R.id.date);
        mDateView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTimeView.getTextSize() / 2);

        mTimeHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Date date = new Date();
                SimpleDateFormat mTimeFormat = new SimpleDateFormat("KK:mm:ss", Locale.getDefault());
                SimpleDateFormat mDateFormat = new SimpleDateFormat("YYYY.MM.dd (E)", Locale.getDefault());
                String timeString = mTimeFormat.format(date);
                String dateString = mDateFormat.format(date);
                mTimeView.setText(timeString);
                mDateView.setText(dateString);
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
