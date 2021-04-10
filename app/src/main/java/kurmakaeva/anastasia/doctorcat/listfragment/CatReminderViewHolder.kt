package kurmakaeva.anastasia.doctorcat.listfragment

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kurmakaeva.anastasia.doctorcat.databinding.CatReminderViewholderBinding
import kurmakaeva.anastasia.doctorcat.model.ReminderData

class CatReminderViewHolder(private val binding: CatReminderViewholderBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(catReminderItem: ReminderData) {
        binding.apply {
            reminderTitleTextView.text = catReminderItem.title
            dateTextView.text = catReminderItem.date
            timeTextView.text = catReminderItem.time
        }

        binding.executePendingBindings()
    }

    object DiffCallback: DiffUtil.ItemCallback<ReminderData>() {
        override fun areItemsTheSame(
            oldItem: ReminderData,
            newItem: ReminderData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReminderData,
            newItem: ReminderData): Boolean {
            return oldItem.id == newItem.id
        }
    }
}