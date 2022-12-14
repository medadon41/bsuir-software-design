package com.example.cupcake.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.cupcake.R

class TimerNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification(isRunning: Boolean, stageName: String, timerName: String, elapsed: Int) {
        val timerIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, TimerNotificationReceiver::class.java).apply {
                putExtra("is_running", isRunning)
                putExtra("stage_name", stageName)
                putExtra("timer_name", timerName)
                putExtra("elapsed_name", elapsed)
            },
            Intent.FILL_IN_DATA or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, TIMER_CHANNEL_ID)
            .setSilent(true)
            .setSmallIcon(R.drawable.cupcake)
            .setContentTitle("$timerName")
            .setContentText("Current stage: $stageName | Time elapsed: $elapsed")
            .addAction(
                if (isRunning) R.drawable.ic_baseline_pause_24 else R.drawable.ic_baseline_play_arrow_24,
                if (isRunning) "Pause" else "Start",
                timerIntent
            )
            .setOngoing(true)
            .build()

        notificationManager.notify(
            0, notification
        )
    }

    companion object {
        const val TIMER_CHANNEL_ID = "timer_channel"
    }
}