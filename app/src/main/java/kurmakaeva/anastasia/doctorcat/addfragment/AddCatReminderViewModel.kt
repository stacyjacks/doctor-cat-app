package kurmakaeva.anastasia.doctorcat.addfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kurmakaeva.anastasia.doctorcat.R
import kurmakaeva.anastasia.doctorcat.model.CatReminderDTO
import kurmakaeva.anastasia.doctorcat.model.ReminderData
import kurmakaeva.anastasia.doctorcat.room.RemindersDataSource

class AddCatReminderViewModel(private val repository: RemindersDataSource): ViewModel() {

    val title = MutableLiveData<String>()
    val catName = MutableLiveData<String>()
    val notes = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val image = MutableLiveData<String>()

    val showSnackbarInt = MutableLiveData<Int>()
    val showSnackbar = MutableLiveData<String>()

    fun onClear() {
        title.value = null
        catName.value = null
        notes.value = null
        date.value = null
        time.value = null
        image.value = null
    }

    fun saveReminder(reminderData: ReminderData) {
        viewModelScope.launch {
            repository.saveReminder(
                CatReminderDTO(
                    reminderData.title,
                    reminderData.catName,
                    reminderData.notes,
                    reminderData.date,
                    reminderData.time,
                    reminderData.image,
                    reminderData.id
                )
            )
        }
    }

    fun validateDataIsEntered(reminderData: ReminderData): Boolean {
        if (reminderData.title.isNullOrEmpty()) {
            showSnackbarInt.value = R.string.error_set_title
            return false
        }

        if (reminderData.catName.isNullOrEmpty()) {
            showSnackbarInt.value = R.string.error_set_cat_name
            return false
        }

        if (reminderData.date.isNullOrEmpty() or reminderData.time.isNullOrEmpty()) {
            showSnackbarInt.value = R.string.error_set_date_time
            return false
        }
        return true
    }
}