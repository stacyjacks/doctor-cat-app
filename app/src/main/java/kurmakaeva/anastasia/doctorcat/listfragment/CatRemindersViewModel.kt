package kurmakaeva.anastasia.doctorcat.listfragment

import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kurmakaeva.anastasia.doctorcat.App
import kurmakaeva.anastasia.doctorcat.R
import kurmakaeva.anastasia.doctorcat.model.CatReminderDTO
import kurmakaeva.anastasia.doctorcat.model.ReminderData
import kurmakaeva.anastasia.doctorcat.room.RemindersDataSource

class CatRemindersViewModel(private val repository: RemindersDataSource): ViewModel() {

    private val _listOfCatReminders = MutableLiveData<List<ReminderData>>()
    val listOfCatReminders: LiveData<List<ReminderData>>
    get() = _listOfCatReminders

    private val _singleCatReminder = MutableLiveData<ReminderData>()
    val singleCatReminder: LiveData<ReminderData>
        get() = _singleCatReminder

    fun loadCatReminders() {
        viewModelScope.launch {
            val list = repository.getAllReminders()
            val dataList = ArrayList<ReminderData>()

            dataList.addAll((list as List<CatReminderDTO>).map {
                ReminderData(
                    it.title,
                    it.catName,
                    it.notes,
                    it.date,
                    it.time,
                    it.image,
                    it.id
                )
            })
            _listOfCatReminders.value = dataList
        }
    }

    fun deleteReminder(reminderData: ReminderData) {

    }

    fun getSingleCatReminder(reminderId: String) {
        val emptyNotes = App.context?.resources?.getString(R.string.missing_notes)
        viewModelScope.launch {
            val reminderDB = repository.getReminder(reminderId)
            val reminder = ReminderData(
                reminderDB?.title,
                reminderDB?.catName,
                reminderDB?.notes ?: emptyNotes,
                reminderDB?.date,
                reminderDB?.time,
                reminderDB?.image,
                reminderId
            )
            _singleCatReminder.value = reminder
        }
    }
}