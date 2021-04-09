package kurmakaeva.anastasia.doctorcat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reminders")
data class CatReminderDTO(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "catName") val catName: String?,
    @ColumnInfo(name = "notes") val notes: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "image") val image: String?,
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString()
)