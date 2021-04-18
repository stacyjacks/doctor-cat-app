package kurmakaeva.anastasia.doctorcat.listfragment

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import kurmakaeva.anastasia.doctorcat.R
import kurmakaeva.anastasia.doctorcat.databinding.CatReminderViewholderBinding
import kurmakaeva.anastasia.doctorcat.model.ReminderData

class CatReminderViewHolder(private val context:
                            Context, private val binding: CatReminderViewholderBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(catReminderItem: ReminderData) {
        binding.apply {
            reminderTitleTextView.text = catReminderItem.title
            dateTextView.text = catReminderItem.date
            timeTextView.text = catReminderItem.time

            Glide.with(context)
                .load(catReminderItem.image)
                .override(60, Target.SIZE_ORIGINAL)
                .circleCrop()
                .placeholder(R.drawable.ic_paw)
                .into(catImageView)
        }

        binding.executePendingBindings()
    }

    object DiffCallback: DiffUtil.ItemCallback<ReminderData>() {
        override fun areItemsTheSame(oldItem: ReminderData, newItem: ReminderData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ReminderData, newItem: ReminderData): Boolean {
            return oldItem == newItem
        }
    }
}