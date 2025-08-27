package sq.mayv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sq.mayv.data.local.entity.MovieDetailsEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovie(movie: MovieDetailsEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovieById(id: Int)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieDetailsEntity>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%'")
    suspend fun searchMovies(query: String): List<MovieDetailsEntity>

    @Query("SELECT * FROM movies ORDER BY popularity DESC LIMIT :limit")
    suspend fun getPopularMovies(limit: Int = 10): List<MovieDetailsEntity>

    @Query("SELECT * FROM movies ORDER BY voteAverage DESC LIMIT :limit")
    suspend fun getTrendingMovies(limit: Int = 10): List<MovieDetailsEntity>

    @Query("SELECT * FROM movies ORDER BY releaseDate DESC LIMIT :limit")
    suspend fun getUpcomingMovies(limit: Int = 10): List<MovieDetailsEntity>

}