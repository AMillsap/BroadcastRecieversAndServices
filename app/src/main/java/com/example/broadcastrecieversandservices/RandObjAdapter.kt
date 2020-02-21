package com.example.broadcastrecieversandservices

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.random_item.view.*

class RandObjAdapter(val randObjList : ArrayList<RandomObject>) : RecyclerView.Adapter<RandObjAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandObjAdapter.ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.random_item, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int = randObjList.size

    override fun onBindViewHolder(holder: RandObjAdapter.ViewHolder, position: Int)
    {
        holder.populateItem(randObjList[position])
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        fun populateItem(randObject : RandomObject)
        {
            val picture = randObject.picture
            itemView.tvRandomStringOne.text = randObject.stringOne
            itemView.tvRandomStringTwo.text = randObject.stringTwo
            itemView.tvRandomStringThree.text = randObject.stringThree
            when(picture)
            {
                "pokemon" -> itemView.ivImage.setImageResource(R.drawable.pokemon_zoo)
                "launcher" -> itemView.ivImage.setImageResource(R.drawable.ic_launcher_foreground)
            }
            itemView.setOnClickListener {
                val notificationIntent = Intent(it.context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(it.context, 0, notificationIntent, 0)
                /*alarmManager.set(android.app.AlarmManager.ELAPSED_REALTIME, 5000, "TAG", AlarmManager.OnAlarmListener {
                    val notification: Notification =
                        NotificationCompat.Builder(it.context, ForegroundService.CHANNEL_ID)
                            .setContentTitle(randObject.stringOne)
                            .setContentText(randObject.stringTwo)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentIntent(pendingIntent)
                            .build()
                },null)*/

            }
        }

    }

}