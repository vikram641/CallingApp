package com.example.callingapp.Utlity

import android.os.Handler
import android.os.Looper

class CallTimer {

    private var seconds = 0
    private val handler = Handler(Looper.getMainLooper())

    fun start(onTick: (String) -> Unit) {
        handler.post(object : Runnable {
            override fun run() {
                seconds++
                val min = seconds / 60
                val sec = seconds % 60
                onTick(String.format("%02d:%02d", min, sec))
                handler.postDelayed(this, 1000)
            }
        })
    }

    fun stop() {
        handler.removeCallbacksAndMessages(null)
    }
}