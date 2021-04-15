package kurmakaeva.anastasia.doctorcat.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import kurmakaeva.anastasia.doctorcat.BuildConfig
import kurmakaeva.anastasia.doctorcat.MainActivity
import kurmakaeva.anastasia.doctorcat.R
import kurmakaeva.anastasia.doctorcat.model.ReminderData

private const val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + ".channel"

fun NotificationManager.sendNotification(context: Context, reminderData: ReminderData) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
        val name = context.getString(R.string.app_name)
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val bundle = Bundle()
    bundle.putString("reminderId", reminderData.id)

    val notificationPendingIntent = NavDeepLinkBuilder(context)
        .setComponentName(MainActivity::class.java)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.catReminderDetailFragment)
        .setArguments(bundle)
        .createPendingIntent()

    val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_paw)
        .setContentTitle(reminderData.title)
        .setContentText(reminderData.catName)
        .setContentIntent(notificationPendingIntent)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(getUniqueId(), notification)
}

private fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())