package com.example.cupcake.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class TimerNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val arguments = intent?.extras
        CurrentTimerState.isRunning = !(arguments?.getBoolean("is_running") ?: false)
        CurrentTimerState.timerName = arguments?.getString("timer_name") ?: "abc"
        CurrentTimerState.stageName = arguments?.getString("stage_name") ?: "abc"
        CurrentTimerState.elapsed = arguments?.getInt("elapsed") ?: 0
        var service = TimerNotificationService(context)
        service.showNotification(
            isRunning = CurrentTimerState.isRunning,
            timerName = CurrentTimerState.timerName,
            stageName = CurrentTimerState.stageName,
            elapsed = CurrentTimerState.elapsed
        )
    }
}