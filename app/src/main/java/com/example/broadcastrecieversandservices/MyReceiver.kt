package com.example.broadcastrecieversandservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver(val receiverCallback: ReceiverCallback) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent)
    {
        val messageReceived = intent.getParcelableArrayListExtra<RandomObject>("KEY")
        Log.d("TAG", "In Recieved : $messageReceived")
        receiverCallback.passInfoToUI(messageReceived!!)
    }
}

interface ReceiverCallback
{
    fun passInfoToUI(passedValue : ArrayList<RandomObject>)
}
