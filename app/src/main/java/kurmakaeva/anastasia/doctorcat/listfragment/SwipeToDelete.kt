package kurmakaeva.anastasia.doctorcat.listfragment

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDelete(val adapter: CatRemindersListAdapter,
                    val viewModel: CatRemindersViewModel
                    ): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private lateinit var deleteIcon: Drawable
    private lateinit var background: ColorDrawable

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val reminder = adapter.currentList[viewHolder.adapterPosition]

        viewModel.deleteReminder(reminder.id)
        viewModel.loadCatReminders()
    }

}
//    val touchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//        private lateinit var deleteIcon: Drawable
//        private lateinit var background: ColorDrawable
//
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            return false
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            val position = viewHolder.adapterPosition
//            val movieId = movieListAdapter.itemIdForPosition(position)
//
//            val uniqueMovieIds = currentMovieList?.movieIds?.toMutableSet()
//            uniqueMovieIds?.remove(movieId)
//            val updatedMovieIds = uniqueMovieIds?.let { ArrayList(it) }
//
//            val updatedMovieList = currentMovieList ?: return
//            updatedMovieList.movieIds = updatedMovieIds
//            movieListRepo.update(updatedMovieList)
//
//        }
//
//        override fun onChildDraw(
//            c: Canvas,
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            dX: Float,
//            dY: Float,
//            actionState: Int,
//            isCurrentlyActive: Boolean
//        ) {
//
//            super.onChildDraw(
//                c,
//                recyclerView,
//                viewHolder,
//                dX,
//                dY,
//                actionState,
//                isCurrentlyActive
//            )
//
//            deleteIcon =
//                ContextCompat.getDrawable(applicationContext, R.drawable.ic_delete_white_24dp)!!
//            background = ColorDrawable(resources.getColor(colorAccent))
//
//            val itemView = viewHolder.itemView
//            val backgroundCornerOffset = 20
//            if (dX > 0) {
//                background.setBounds(
//                    itemView.left,
//                    itemView.top,
//                    itemView.left + dX.toInt() + backgroundCornerOffset,
//                    itemView.bottom
//                )
//            } else if (dX < 0) { // Swiping to the left
//                background.setBounds(
//                    itemView.right + dX.toInt() - backgroundCornerOffset,
//                    itemView.top, itemView.right, itemView.bottom
//                )
//            } else { // view is unSwiped
//                background.setBounds(0, 0, 0, 0)
//            }
//
//            val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
//            val iconTop = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
//            val iconBottom = iconTop + deleteIcon.intrinsicHeight
//            if (dX > 0) { // Swiping to the right
//                val iconLeft = itemView.left + iconMargin + deleteIcon.intrinsicWidth
//                val iconRight = itemView.left + iconMargin
//                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//                background.setBounds(
//                    itemView.left,
//                    itemView.top,
//                    itemView.left + dX.toInt() + backgroundCornerOffset,
//                    itemView.bottom
//                )
//            } else if (dX < 0) { // Swiping to the left
//                val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
//                val iconRight = itemView.right - iconMargin
//                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//                background.setBounds(
//                    (itemView.right + dX.toInt()) - backgroundCornerOffset,
//                    itemView.top,
//                    itemView.right,
//                    itemView.bottom
//                )
//            } else {
//                background.setBounds(0, 0, 0, 0)
//            }
//            background.draw(c)
//            deleteIcon.draw(c)
//        }
//    }
//
//    val itemTouchHelper = ItemTouchHelper(touchCallback)
//    itemTouchHelper.attachToRecyclerView(movieItemsRecyclerView)
//}