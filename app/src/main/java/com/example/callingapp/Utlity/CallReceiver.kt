package com.example.callingapp.Utlity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.example.callingapp.viewModel.CallViewModel

class CallReceiver(private val viewModel: CallViewModel) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {

            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

            when (state) {

                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    // Call started
                    viewModel.onCallStarted()
                }

                TelephonyManager.EXTRA_STATE_IDLE -> {
                    // Call ended
                    viewModel.onCallEnded()
                }
            }
        }
    }
}