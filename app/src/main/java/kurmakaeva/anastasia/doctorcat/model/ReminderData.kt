package kurmakaeva.anastasia.doctorcat.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ReminderData(
    val title: String?,
    val catName: String?,
    val notes: String?,
    val date: String?,
    val time: String?,
    var image: String?,
    val id: String = UUID.randomUUID().toString()
): Parcelable
