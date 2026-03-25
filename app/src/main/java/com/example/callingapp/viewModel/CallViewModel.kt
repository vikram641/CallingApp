package com.example.callingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.callingapp.model.CallStatus

class CallViewModel : ViewModel() {

    private val _callStatus = MutableLiveData<CallStatus>(CallStatus.Idle)
    val callStatus: LiveData<CallStatus> = _callStatus

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _callDuration = MutableLiveData(0)
    val callDuration: LiveData<Int> = _callDuration

    // Set number before calling
    fun setPhoneNumber(number: String) {
        _phoneNumber.value = number
    }

    // Called when real call starts (OFFHOOK)
    fun onCallStarted() {
        _callStatus.value = CallStatus.Started
    }

    // Called when call ends (IDLE)
    fun onCallEnded() {
        _callStatus.value = CallStatus.Ended
        _callDuration.value = 0
    }

    // Update timer from UI / Handler
    fun updateCallDuration(seconds: Int) {
        _callDuration.value = seconds
    }

    // Reset to idle after handling end
    fun resetToIdle() {
        _callStatus.value = CallStatus.Idle
    }
}