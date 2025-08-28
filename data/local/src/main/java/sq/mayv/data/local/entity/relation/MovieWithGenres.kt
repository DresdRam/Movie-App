package sq.mayv.data.local.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import sq.mayv.data.local.entity.GenreEntity
import sq.mayv.data.local.entity.MovieDetailsEntity
import sq.mayv.data.local.entity.MovieGenreCross

data class MovieWithGenres(
    @Embedded val movie: MovieDetailsEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MovieGenreCross::class,
            parentColumn = "movieId",
            entityColumn = "genreId"
        )
    )
    val genres: List<GenreEntity>
)