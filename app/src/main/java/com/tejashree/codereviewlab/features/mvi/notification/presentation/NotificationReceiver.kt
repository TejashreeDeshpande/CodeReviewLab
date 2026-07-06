package com.tejashree.codereviewlab.features.mvi.notification.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tejashree.codereviewlab.features.mvi.notification.data.model.NotificationType
import com.tejashree.codereviewlab.features.mvi.notification.data.model.Priority
import com.tejashree.codereviewlab.features.mvi.notification.data.model.SmartNotification

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra("id", 0)
        
        // Handle Smart Actions (Snooze / Archive)
        when (intent.action) {
            LocalNotificationHelper.ACTION_ARCHIVE -> {
                // TODO: In a real app, update the Repository/MVI state here
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                notificationManager.cancel(id)
                return
            }
            LocalNotificationHelper.ACTION_SNOOZE -> {
                // TODO: Reschedule notification using AlarmManager/WorkManager
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                notificationManager.cancel(id)
                return
            }
        }

        val title = intent.getStringExtra("title") ?: "Reminder"
        val message = intent.getStringExtra("message") ?: ""
        val priorityName = intent.getStringExtra("priority") ?: Priority.MEDIUM.name
        val typeName = intent.getStringExtra("type") ?: NotificationType.WORK.name
        
        val notification = SmartNotification(
            id = id,
            title = title,
            message = message,
            priority = Priority.valueOf(priorityName),
            type = NotificationType.valueOf(typeName),
            time = "Scheduled"
        )

        val helper = LocalNotificationHelper(context)
        helper.showNotification(notification)
    }
}
