package kurmakaeva.anastasia.doctorcat.model

import java.util.*

data class ReminderData(
    val title: String?,
    val catName: String?,
    val notes: String?,
    val date: String?,
    val time: String?,
    val image: String?,
    val id: String = UUID.randomUUID().toString()
)
