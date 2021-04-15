package kurmakaeva.anastasia.doctorcat.addfragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kurmakaeva.anastasia.doctorcat.databinding.FragmentAddCatReminderBinding
import kurmakaeva.anastasia.doctorcat.model.ReminderData
import kurmakaeva.anastasia.doctorcat.transformIntoDatePicker
import kurmakaeva.anastasia.doctorcat.transformIntoTimePicker
import kurmakaeva.anastasia.doctorcat.utils.AlertReceiver
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalTime
import java.util.*


class AddCatReminderFragment: Fragment() {

    private lateinit var binding: FragmentAddCatReminderBinding
    private val viewModel by viewModel<AddCatReminderViewModel>()
    private lateinit var reminderData: ReminderData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        binding = FragmentAddCatReminderBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()

        binding.apply {
            selectDateEditText
                .transformIntoDatePicker(requireContext(), "dd/MM/YYYY", Date(), calendar)
            selectTimeEditText
                .transformIntoTimePicker(requireContext(), "HH:mm", LocalTime.now(), calendar)

            uploadPictureButton.setOnClickListener {
                // selectImage()
            }
        }

        binding.saveButton.setOnClickListener {
            val title = viewModel.title.value
            val catName = viewModel.catName.value
            val notes = viewModel.notes.value
            val date = viewModel.date.value
            val time = viewModel.time.value
            val image = viewModel.image.value

            reminderData = ReminderData(
                title = title,
                catName = catName,
                notes = notes,
                date = date,
                time = time,
                image = image,
            )

            viewModel.saveReminder(reminderData)

            setAlarm(calendar)

            val action = AddCatReminderFragmentDirections.actionAddCatReminderFragmentToCatRemindersListFragment()
            this.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onClear()
    }

    private fun setAlarm(calendar: Calendar) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlertReceiver::class.java)

        intent.action = "NOTE_ACTION"

        val bundle = Bundle()
        bundle.putParcelable("NOTE_DATA", reminderData)
        intent.putExtra("NOTE_DATA", bundle)

        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 1, intent, 0)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)
    }

    private fun selectImage() {

    }
}