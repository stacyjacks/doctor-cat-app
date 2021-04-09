package kurmakaeva.anastasia.doctorcat.room

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kurmakaeva.anastasia.doctorcat.model.CatReminderDTO

class CatRemindersRepo(
    private val remindersDao: RemindersDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : RemindersDataSource {

    override suspend fun getAllReminders(): List<CatReminderDTO> = withContext(ioDispatcher) {
        return@withContext remindersDao.getAllReminders()
    }

    override suspend fun saveReminder(reminder: CatReminderDTO) = withContext(ioDispatcher) {
        remindersDao.saveReminder(reminder)
    }

    override suspend fun getReminder(reminderId: String): CatReminderDTO? = withContext(ioDispatcher) {
        return@withContext remindersDao.getReminderById(reminderId)
    }

    override suspend fun deleteReminder(reminder: CatReminderDTO) = withContext(ioDispatcher) {
        return@withContext remindersDao.deleteReminder(reminder)
    }
}