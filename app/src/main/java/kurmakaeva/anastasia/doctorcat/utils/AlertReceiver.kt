package kurmakaeva.anastasia.doctorcat.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import kurmakaeva.anastasia.doctorcat.model.ReminderData

class AlertReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat
            .getSystemService(context, NotificationManager::class.java) as NotificationManager

        if (intent.action == "NOTE_ACTION") {
            val bundle: Bundle? = intent.getBundleExtra("NOTE_DATA")
            val reminderData: ReminderData? = bundle?.getParcelable("NOTE_DATA")

            notificationManager.sendNotification(context, reminderData!!)
        }
    }
}