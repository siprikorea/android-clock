package com.siprikorea.clock

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    /**
     * on create
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // text view
        val mTimeView: TextView = findViewById(R.id.time)
        val mDateView: TextView = findViewById(R.id.date)

        // time format
        val mTimeFormat = SimpleDateFormat("KK:mm:ss", Locale.ENGLISH)
        val mDateFormat = SimpleDateFormat("yyyy. MM. dd EEE", Locale.ENGLISH)

        // time handler
        mTimeHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val date = Date()
                mTimeView.text = mTimeFormat.format(date)
                mDateView.text = mDateFormat.format(date)
            }
        }

        runTimer()
    }

    /**
     * run timer
     */
    private fun runTimer() {
        Thread {
            while (true) {
                try {
                    mTimeHandler!!.sendMessage(mTimeHandler!!.obtainMessage())
                    Thread.sleep(1000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    companion object {
        private var mTimeHandler: Handler? = null
    }
}