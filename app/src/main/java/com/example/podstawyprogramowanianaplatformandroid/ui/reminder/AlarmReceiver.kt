package com.example.podstawyprogramowanianaplatformandroid.ui.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.podstawyprogramowanianaplatformandroid.R

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ID = "CHANEL_ID"
        const val chanelName = "CHANEL_NAME"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelNotification: NotificationCompat.Builder = NotificationCompat.Builder (context, ID)
            .setContentTitle(intent?.extras?.get("TITLE") as String)
            .setContentText(intent.extras?.get("TEXT") as String)
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)

        val channel = NotificationChannel(ID, chanelName, NotificationManager.IMPORTANCE_HIGH)

        manager.createNotificationChannel(channel)
        manager.notify(intent.extras?.get("ID") as Int,channelNotification.build())
    }
}