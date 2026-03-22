package com.example.callingapp.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.callingapp.model.CallState

class CallViewModel: ViewModel() {

    private val _callState = MutableLiveData(CallState.IDLE)
    val callState: LiveData<CallState> = _callState

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    fun startCall(number: String) {
        _phoneNumber.value = number
        _callState.value = CallState.CALLING


        Handler(Looper.getMainLooper()).postDelayed({
            _callState.value = CallState.RINGING
        }, 6000)
    }

    fun acceptCall() {
        _callState.value = CallState.ACTIVE
    }

    fun rejectCall() {
        _callState.value = CallState.ENDED
    }

    fun endCall() {
        _callState.value = CallState.ENDED
    }

    fun resetCall() {
        _callState.value = CallState.IDLE
    }
}