package kurmakaeva.anastasia.doctorcat.model

data class CatReminderDTO(
    val title: String,
    val catName: String,
    val description: String,
    val date: String,
    val time: String
)