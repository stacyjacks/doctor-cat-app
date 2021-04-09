package kurmakaeva.anastasia.doctorcat.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kurmakaeva.anastasia.doctorcat.model.CatReminderDTO

@Database(entities = [CatReminderDTO::class], version = 2, exportSchema = false)
abstract class RemindersDatabase: RoomDatabase() {
    abstract fun remindersDao(): RemindersDao
}

object RoomDB {
    fun createRemindersDao(context: Context): RemindersDao {
        return Room
            .databaseBuilder(context.applicationContext, RemindersDatabase::class.java, "catReminders.db")
            .fallbackToDestructiveMigration()
            .build()
            .remindersDao()
    }
}