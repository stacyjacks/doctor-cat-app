package kurmakaeva.anastasia.doctorcat.listfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kurmakaeva.anastasia.doctorcat.databinding.CatReminderViewholderBinding
import kurmakaeva.anastasia.doctorcat.model.ReminderData

interface SelectableReminder {
    fun selectedReminder(reminderId: String)
}

class CatRemindersListAdapter(private val selectableReminder: SelectableReminder)
    : ListAdapter<ReminderData, CatReminderViewHolder>(CatReminderViewHolder.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatReminderViewHolder {
        val binding = CatReminderViewholderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return CatReminderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatReminderViewHolder, position: Int) {
        val catReminderItem = getItem(position)

        if (catReminderItem != null) {
            holder.bind(catReminderItem)
        }

        holder.itemView.setOnClickListener {
            selectableReminder.selectedReminder(catReminderItem.id)
        }
    }
}