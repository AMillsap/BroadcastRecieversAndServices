package com.example.broadcastrecieversandservices

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


class ForegroundService : Service()
{
    lateinit var mediaPlayer : MediaPlayer

    override fun onCreate()
    {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.never_gonna_give_you_up)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        val input = intent.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)

        val stopIntent = Intent(this, ForegroundService::class.java)
        stopIntent.action = "stop.media.player"
        val pendIntent = PendingIntent.getService(this, 0, stopIntent, 0)

        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Media Player")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_foreground, "Stop", pendIntent)
                .build()

        startForeground(1, notification)
        mediaPlayer.start()


        if(intent.action == "stop.media.player")
        {
            stopService(stopIntent)
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = ContextCompat.getSystemService<NotificationManager>(this,
                NotificationManager::class.java
            )
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

}
