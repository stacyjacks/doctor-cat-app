package kurmakaeva.anastasia.doctorcat.listfragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kurmakaeva.anastasia.doctorcat.App
import kurmakaeva.anastasia.doctorcat.R

class SwipeToDelete(private val adapter: CatRemindersListAdapter,
                    val viewModel: CatRemindersViewModel
                    ): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private lateinit var deleteIcon: Drawable
    private lateinit var background: ColorDrawable

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val reminder = adapter.currentList[viewHolder.adapterPosition]

        viewModel.deleteReminder(reminder.id)

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(300)
            viewModel.loadCatReminders()
        }
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        super.onChildDraw(
            canvas,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )

        deleteIcon = ContextCompat.getDrawable(App.context!!.applicationContext, R.drawable.ic_delete)!!
        background = ColorDrawable(Color.RED)

        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20
        when {
            dX > 0 -> {
                background.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset,
                    itemView.bottom
                )
            }
            dX < 0 -> { // Swiping to the left
                background.setBounds(
                    itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top, itemView.right, itemView.bottom
                )
            }
            else -> { // view is unSwiped
                background.setBounds(0, 0, 0, 0)
            }
        }

        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
        val iconBottom = iconTop + deleteIcon.intrinsicHeight
        when {
            dX > 0 -> { // Swiping to the right
                val iconLeft = itemView.left + iconMargin + deleteIcon.intrinsicWidth
                val iconRight = itemView.left + iconMargin
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset,
                    itemView.bottom
                )
            }
            dX < 0 -> { // Swiping to the left
                val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setBounds(
                    (itemView.right + dX.toInt()) - backgroundCornerOffset,
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
            }
            else -> {
                background.setBounds(0, 0, 0, 0)
            }
        }

        background.draw(canvas)
        deleteIcon.draw(canvas)
    }
}