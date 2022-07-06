package stay.example.fathersdayjesserating

data class Rank (
    val id: String,
    val image: String?,
    var score: String,
    var likeOn: Boolean,
    var dislikeOn: Boolean
        )