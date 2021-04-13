package kurmakaeva.anastasia.doctorcat.listfragment

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
import kurmakaeva.anastasia.doctorcat.service.CatFactsApiResponse
import kurmakaeva.anastasia.doctorcat.service.CatFactsApiService

class CatRemindersViewModel(private val repository: RemindersDataSource): ViewModel() {

    private val _listOfCatReminders = MutableLiveData<List<ReminderData>>()
    val listOfCatReminders: LiveData<List<ReminderData>>
    get() = _listOfCatReminders

    private val _singleCatReminder = MutableLiveData<ReminderData>()
    val singleCatReminder: LiveData<ReminderData>
        get() = _singleCatReminder

    private val _catFact = MutableLiveData<CatFactsApiResponse>()
    val catFact: LiveData<CatFactsApiResponse>
        get() = _catFact

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

    fun deleteReminder(reminderId: String) {
        viewModelScope.launch {
            repository.deleteReminder(reminderId)
        }
    }

    fun getCatFact() {
        val catFactsApiService = CatFactsApiService.instance
        viewModelScope.launch {
            _catFact.value = catFactsApiService.getCatFact()
        }
    }
}