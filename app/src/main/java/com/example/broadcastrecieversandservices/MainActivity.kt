package com.example.broadcastrecieversandservices

import android.app.AlarmManager
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ReceiverCallback
{
    val intentFilter = IntentFilter()
    val localReciever = MyReceiver(this)

    var itemsInArray = populateRandomItems()
    var adapter = RandObjAdapter(itemsInArray)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intentFilter.addAction("stop.media.player")
        intentFilter.addAction("com.example.broadcastrecieversandservices.MyReceiver")
        initRecyclerView()
    }

    override fun onStart()
    {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(localReciever, intentFilter)
    }

    override fun onStop()
    {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReciever)
    }
    fun onCLick(view: View)
    {
        when(view.id)
        {
            R.id.btnStartService -> startService()
            R.id.btnFillList -> fillRecyclerView()
        }
    }

    private fun fillRecyclerView()
    {
        Log.d("TAG", "I have been pressed")
        Intent().also { intent ->
            intent.setAction("com.example.broadcastrecieversandservices.MyReceiver")
            intent.putParcelableArrayListExtra("KEY", populateRandomItems())
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }

    }

    fun startService()
    {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        serviceIntent.putExtra("inputExtra", "Playing Never Gonna Give You Up")
        ContextCompat.startForegroundService(this, serviceIntent)
    }


    override fun passInfoToUI(passedValue: ArrayList<RandomObject>)
    {
        itemsInArray.clear()
        itemsInArray.addAll(passedValue)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView()
    {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rvRandomList.layoutManager = layoutManager
        rvRandomList.addItemDecoration((itemDecoration))
        rvRandomList.adapter = adapter
    }

    fun populateRandomItems() : ArrayList<RandomObject>
    {
        var randomList = ArrayList<RandomObject>()
        var rvRandomList = ArrayList<RandomObject>()
        randomList.add(RandomObject("Random", "Food", "Somethng", "pokemon"))
        randomList.add(RandomObject("Hello", "Upstairs", "Yes", "launcher"))
        randomList.add(RandomObject("Goodbye", "Downstairs", "Picture", "pokemon"))
        randomList.add(RandomObject("Something", "Table", "Someday", "launcher"))
        randomList.add(RandomObject("Drinks", "Nothing", "Cat", "pokemon"))
        randomList.add(RandomObject("NUmbers", "Everything", "Dog", "launcher"))

        var randomPicker = (0..5).random()
        for (i in 0..3)
        {
            rvRandomList.add(randomList[randomPicker])
            randomPicker = (0..5).random()
        }
        return rvRandomList
    }


}
