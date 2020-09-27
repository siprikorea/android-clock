package com.siprikorea.clock;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    TextView mTimeCtrl;
    TextView mDateCtrl;
    static Handler mTimeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mDateCtrl = findViewById(R.id.date);
        mTimeCtrl = findViewById(R.id.time);
        mDateCtrl.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTimeCtrl.getTextSize() / 2);

        mTimeHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Date date = new Date();
                String timeString = DateFormat.getTimeFormat(mContext).format(date);
                String dateString = DateFormat.getDateFormat(mContext).format(date);
                mTimeCtrl.setText(timeString);
                mDateCtrl.setText(dateString);
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
                        Message msg = mTimeHandler.obtainMessage();
                        mTimeHandler.sendMessage(msg);

                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
