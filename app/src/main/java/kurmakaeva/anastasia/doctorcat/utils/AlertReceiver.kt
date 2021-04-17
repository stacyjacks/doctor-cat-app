package kurmakaeva.anastasia.doctorcat.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlertReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "NOTE_ACTION") {
            NotificationService.enqueueWork(context, intent)
        }
    }
}

