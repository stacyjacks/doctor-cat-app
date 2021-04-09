package kurmakaeva.anastasia.doctorcat.room

import androidx.room.*
import kurmakaeva.anastasia.doctorcat.model.CatReminderDTO

@Dao
interface RemindersDao {
    // List all reminders

    @Query("SELECT * FROM reminders")
    suspend fun getAllReminders(): List<CatReminderDTO>

    // Get reminders by its ID
    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getReminderById(id: String): CatReminderDTO?

    // Insert a reminder in the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReminder(reminder: CatReminderDTO)

    // Delete a reminder
    @Delete
    suspend fun deleteReminder(reminder: CatReminderDTO)

}