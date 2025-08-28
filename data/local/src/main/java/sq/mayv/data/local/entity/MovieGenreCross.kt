package sq.mayv.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "movie_genre_cross",
    primaryKeys = ["movieId", "genreId"]
)
data class MovieGenreCross(
    val movieId: Int,
    val genreId: Int
)