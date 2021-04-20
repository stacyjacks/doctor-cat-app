package kurmakaeva.anastasia.doctorcat.addfragment

import android.app.Activity.RESULT_OK
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import kurmakaeva.anastasia.doctorcat.R
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
    private lateinit var reminderData: ReminderData
    private var imageUri: String? = null

    private val viewModel by viewModel<AddCatReminderViewModel>()
    private val SELECT_IMAGE = 500
    private val TAG_ADD = "TAG_ADD"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

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

            uploadPicture.setOnClickListener {
                selectImageFromGallery()
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

            if (imageUri != null) {
                reminderData.image = imageUri
            }

            if (viewModel.validateDataIsEntered(reminderData)) {
                viewModel.saveReminder(reminderData)

                setAlarm(calendar)
                showSuccessToast()

                val action = AddCatReminderFragmentDirections.actionAddCatReminderFragmentToCatRemindersListFragment()
                this.findNavController().navigate(action)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.showSnackbar.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.showSnackbarInt.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Snackbar.make(requireView(), getString(it), Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onClear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(TAG_ADD, "onActivityResultCalled")
        if (resultCode == RESULT_OK && requestCode == SELECT_IMAGE) {
            imageUri = data?.data.toString()
            onImageSelected()
        }
    }

    private fun setAlarm(calendar: Calendar) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlertReceiver::class.java)

        intent.action = "NOTE_ACTION"

        val bundle = Bundle()
        bundle.putParcelable("NOTE_DATA", reminderData)
        intent.putExtra("NOTE_DATA", bundle)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            SystemClock.uptimeMillis().toInt(),
            intent,
            0
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)
    }

    private fun showSuccessToast() {
        Toast
            .makeText(requireContext(), getString(R.string.success_reminder_set), Toast.LENGTH_LONG)
            .show()
    }

    private fun selectImageFromGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, SELECT_IMAGE)
    }

    private fun onImageSelected() {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(150, Target.SIZE_ORIGINAL)
            .circleCrop()

        Glide.with(this)
            .load(imageUri)
            .apply(requestOptions)
            .into(binding.uploadPicture)

        binding.uploadPictureFeedback.text = getString(R.string.success_picture_set)
    }
}