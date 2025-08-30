package sq.mayv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import sq.mayv.data.local.entity.GenreEntity
import sq.mayv.data.local.entity.MovieDetailsEntity
import sq.mayv.data.local.entity.relation.MovieWithGenres

@Dao
interface MoviesDao {
    @Transaction
    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieWithGenres?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDetailsEntity>)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovieById(id: Int)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Transaction
    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieWithGenres>

    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<GenreEntity>

    @Transaction
    @Query("SELECT * FROM movies ORDER BY releaseDate DESC")
    suspend fun getUpcomingMovies(): List<MovieWithGenres>

    @Transaction
    @Query("SELECT * FROM movies ORDER BY voteCount DESC")
    suspend fun getTopRatedMovies(): List<MovieWithGenres>

    @Transaction
    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    suspend fun getPopularMovies(): List<MovieWithGenres>

    @Transaction
    @Query("SELECT * FROM movies ORDER BY voteAverage DESC")
    suspend fun getTrendingMovies(): List<MovieWithGenres>

}