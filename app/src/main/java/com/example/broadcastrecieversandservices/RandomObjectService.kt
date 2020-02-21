package com.example.broadcastrecieversandservices

import android.app.IntentService
import android.content.Intent
import android.content.Context
import kotlin.random.Random

// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_CREATE_RAND_ITEM = "com.example.broadcastrecieversandservices.action.FOO"

private const val NUMBER_OF_ITEMS = "com.example.broadcastrecieversandservices.extra.PARAM1"
private const val EXTRA_PARAM2 = "com.example.broadcastrecieversandservices.extra.PARAM2"

class RandomObjectService : IntentService("RandomObjectService")
{

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_CREATE_RAND_ITEM -> {
                val param1 = intent.getStringExtra(NUMBER_OF_ITEMS)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionRandomItem(param1, param2)
            }
        }
    }

    private fun handleActionRandomItem(param1: String, param2: String)
    {
        var objectList = ArrayList<RandomObject>()
        for( i in 1..5)
        {
            //objectList.add(RandomObject(Random.toString(), Random.toString(), Random.toString()))
        }

    }


    companion object
    {

        @JvmStatic
        fun startActionRandomItem(context: Context, param1: String, param2: String) {
            val intent = Intent(context, RandomObjectService::class.java).apply {
                action = ACTION_CREATE_RAND_ITEM
                putExtra(NUMBER_OF_ITEMS, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }

    }
}
