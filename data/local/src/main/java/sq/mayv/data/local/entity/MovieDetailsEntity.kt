package sq.mayv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDetailsEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val title: String,
    val originalTitle: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val releaseDate: Long,
    val budget: Long,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String?,
    val video: Boolean
)