package sq.mayv.feature.home.ui.state

data class MovieDetailsUI(
    val id: Int,
    val isAdult: Boolean,
    val title: String,
    val originalTitle: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val releaseDate: String,
    val budget: Long,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String?,
    val hasVideo: Boolean,
    val genres: List<GenreUI>
)
