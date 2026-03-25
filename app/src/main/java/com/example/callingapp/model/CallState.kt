package com.example.callingapp.model

sealed class CallStatus {
    object Idle : CallStatus()
    object Dialing : CallStatus()
    object Started : CallStatus()
    object Ended : CallStatus()
}