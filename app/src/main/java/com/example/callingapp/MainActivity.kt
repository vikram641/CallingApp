package com.example.callingapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.callingapp.VIew.DialPadFragment

import com.example.callingapp.VIew.OutgoingCallFragment
import com.example.callingapp.VIew.activeCallFragment
import com.example.callingapp.model.CallState
import com.example.callingapp.view.IncomingCallDialogFragment
import com.example.callingapp.viewModel.CallViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var callViewModel: CallViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        callViewModel = ViewModelProvider(this)[CallViewModel::class.java]

        observeState()
    }

    private fun observeState() {
        callViewModel.callState.observe(this) { state ->
            when (state) {
                CallState.IDLE -> loadFragment(DialPadFragment())

                CallState.CALLING -> loadFragment(OutgoingCallFragment())

                CallState.RINGING -> {
                    if (supportFragmentManager.findFragmentByTag("Incoming") == null) {
                        IncomingCallDialogFragment().show(
                            supportFragmentManager,
                            "Incoming"
                        )
                    }
                }

                CallState.ACTIVE -> loadFragment(activeCallFragment())

                CallState.ENDED -> {
                    Toast.makeText(this, "Call Ended", Toast.LENGTH_SHORT).show()
                    callViewModel.resetCall()
                }
            }
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, fragment)
            .commit()
    }
}