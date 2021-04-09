package kurmakaeva.anastasia.doctorcat.addfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kurmakaeva.anastasia.doctorcat.databinding.FragmentAddCatReminderBinding
import kurmakaeva.anastasia.doctorcat.model.ReminderData
import kurmakaeva.anastasia.doctorcat.transformIntoDatePicker
import kurmakaeva.anastasia.doctorcat.transformIntoTimePicker
import java.time.LocalTime
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddCatReminderFragment: Fragment() {

    private lateinit var binding: FragmentAddCatReminderBinding
    private val viewModel by viewModel<AddCatReminderViewModel>()
    private lateinit var reminderData: ReminderData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentAddCatReminderBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            selectDateEditText
                .transformIntoDatePicker(requireContext(), "dd/MM/YYYY", Date())
            selectTimeEditText
                .transformIntoTimePicker(requireContext(), "HH:mm", LocalTime.now())

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
                image = image
            )

            viewModel.saveReminder(reminderData)

            val action = AddCatReminderFragmentDirections.actionAddCatReminderFragmentToCatRemindersListFragment()
            this.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onClear()
    }

    private fun selectImage() {

    }
}