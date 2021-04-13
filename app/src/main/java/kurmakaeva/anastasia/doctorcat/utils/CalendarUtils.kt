package kurmakaeva.anastasia.doctorcat

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

fun EditText.transformIntoDatePicker(context: Context, format: String, minDate: Date? = null) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val calendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.ENGLISH)
            setText(sdf.format(calendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context,
            R.style.CalendarTheme,
            datePickerOnDataSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .run {
                minDate?.time?.also { datePicker.minDate = it }
                show()
            }
    }
}

fun EditText.transformIntoTimePicker(context: Context, format: String, minTime: LocalTime) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val calendar = Calendar.getInstance()
    val timePickerOnDataSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minuteOfHour)

            val sdf = SimpleDateFormat(format, Locale.ENGLISH)
            setText(sdf.format(calendar.time))
        }

    setOnClickListener {
        TimePickerDialog(
            context,
            R.style.CalendarTheme,
            timePickerOnDataSetListener,
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        )
            .run {
                minTime.minute
                show()
            }

    }
}