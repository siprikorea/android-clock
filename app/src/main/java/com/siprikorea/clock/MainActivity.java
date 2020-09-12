package com.siprikorea.clock;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    SimpleDateFormat mDateFormat = new SimpleDateFormat("M월 d일 (E)", Locale.getDefault());
    SimpleDateFormat mTimeFormat = new SimpleDateFormat("KK:mm:ss", Locale.getDefault());
    String mDateString;
    String mTimeString;
    TextView mDateCtrl;
    TextView mTimeCtrl;
    static Handler mProgressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDateCtrl = findViewById(R.id.date);
        mTimeCtrl = findViewById(R.id.time);
        mDateCtrl.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTimeCtrl.getTextSize() / 2);

        mProgressHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String[] dateTime = (String[])msg.obj;
                mDateCtrl.setText(dateTime[0]);
                mTimeCtrl.setText(dateTime[1]);
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
                        Date date = new Date();
                        mDateString = mDateFormat.format(date);
                        mTimeString = mTimeFormat.format(date);

                        Message msg = mProgressHandler.obtainMessage();
                        msg.obj = new String[]{ mDateString, mTimeString };
                        mProgressHandler.sendMessage(msg);

                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
