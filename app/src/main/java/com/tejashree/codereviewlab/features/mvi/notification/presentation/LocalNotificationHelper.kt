package com.tejashree.codereviewlab.features.mvi.notification.presentation

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.tejashree.codereviewlab.features.mvi.notification.data.model.Priority
import com.tejashree.codereviewlab.features.mvi.notification.data.model.SmartNotification

class LocalNotificationHelper(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    companion object {
        const val CHANNEL_ID = "smart_inbox_notifications"
        const val CHANNEL_NAME = "Smart Inbox"
        
        // Actions for Smart features
        const val ACTION_SNOOZE = "com.tejashree.codereviewlab.ACTION_SNOOZE"
        const val ACTION_ARCHIVE = "com.tejashree.codereviewlab.ACTION_ARCHIVE"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH // Increased to HIGH for smart experience
            ).apply {
                description = "Notifications for Smart Inbox reminders"
                enableLights(true)
                setShowBadge(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(notification: SmartNotification) {
        val priority = when (notification.priority) {
            Priority.HIGH -> NotificationCompat.PRIORITY_HIGH
            Priority.MEDIUM -> NotificationCompat.PRIORITY_DEFAULT
            Priority.LOW -> NotificationCompat.PRIORITY_LOW
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(notification.title)
            .setContentText(notification.message)
            .setPriority(priority)
            .setGroup(notification.type.name) // Smart grouping by category
            .setAutoCancel(true)
            
        // Implementation of Snooze & Archive Actions (Placeholders)
        val snoozePendingIntent = getActionPendingIntent(notification.id, ACTION_SNOOZE)
        val archivePendingIntent = getActionPendingIntent(notification.id, ACTION_ARCHIVE)

        builder.addAction(android.R.drawable.ic_menu_recent_history, "Snooze", snoozePendingIntent)
        builder.addAction(android.R.drawable.ic_menu_save, "Archive", archivePendingIntent)

        notificationManager.notify(notification.id, builder.build())
    }

    private fun getActionPendingIntent(id: Int, action: String): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            this.action = action
            putExtra("id", id)
        }
        return PendingIntent.getBroadcast(
            context,
            id + action.hashCode(), // Unique request code per action
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun scheduleNotification(notification: SmartNotification, delayMs: Long) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("id", notification.id)
            putExtra("title", notification.title)
            putExtra("message", notification.message)
            putExtra("priority", notification.priority.name)
            putExtra("type", notification.type.name)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notification.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerAtMillis = System.currentTimeMillis() + delayMs

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        }
    }
}
