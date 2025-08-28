package sq.mayv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import sq.mayv.data.local.dao.MoviesDao
import sq.mayv.data.local.entity.GenreEntity
import sq.mayv.data.local.entity.MovieDetailsEntity
import sq.mayv.data.local.entity.MovieGenreCross

@Database(
    entities = [
        MovieDetailsEntity::class,
        GenreEntity::class,
        MovieGenreCross::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract val moviesDao: MoviesDao

    companion object {
        const val DATABASE_NAME = "MoviesDatabase"
    }
}