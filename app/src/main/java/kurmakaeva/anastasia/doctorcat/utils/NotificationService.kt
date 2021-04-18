package kurmakaeva.anastasia.doctorcat.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.core.app.JobIntentService
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kurmakaeva.anastasia.doctorcat.model.ReminderData
import kotlin.coroutines.CoroutineContext

class NotificationService: JobIntentService(), CoroutineScope {

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, NotificationService::class.java, SystemClock.uptimeMillis().toInt(), intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        val notificationManager = ContextCompat
            .getSystemService(this, NotificationManager::class.java) as NotificationManager

        if (intent.action == "NOTE_ACTION") {
            val bundle: Bundle? = intent.getBundleExtra("NOTE_DATA")
            val reminderData: ReminderData? = bundle?.getParcelable("NOTE_DATA")

            notificationManager.sendNotification(this, reminderData!!)
        }
    }
}