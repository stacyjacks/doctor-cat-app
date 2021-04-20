package kurmakaeva.anastasia.doctorcat.room

import androidx.room.*
import kurmakaeva.anastasia.doctorcat.model.CatReminderDTO

@Dao
interface RemindersDao {
    // List all reminders

    @Query("SELECT * FROM reminders ORDER BY date ASC, time ASC")
    suspend fun getAllReminders(): List<CatReminderDTO>

    // Get reminders by its ID
    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getReminderById(id: String): CatReminderDTO?

    // Insert a reminder in the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReminder(reminder: CatReminderDTO)

    // Delete a reminder
    @Query("DELETE from reminders where id = :reminderId")
    suspend fun deleteReminder(reminderId: String)

}