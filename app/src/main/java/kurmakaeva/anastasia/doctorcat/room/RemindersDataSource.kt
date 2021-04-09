package kurmakaeva.anastasia.doctorcat.room

import kurmakaeva.anastasia.doctorcat.model.CatReminderDTO

interface RemindersDataSource {
    suspend fun getAllReminders(): List<CatReminderDTO>

    suspend fun saveReminder(reminder: CatReminderDTO)

    suspend fun getReminder(reminderId: String): CatReminderDTO?

    suspend fun deleteReminder(reminder: CatReminderDTO)
}