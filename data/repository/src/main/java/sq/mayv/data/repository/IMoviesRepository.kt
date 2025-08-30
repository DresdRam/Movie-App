package sq.mayv.data.repository

import kotlinx.coroutines.flow.Flow
import sq.mayv.core.common.GenericState
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails

interface IMoviesRepository {

    fun loadUpcomingMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>>

    fun loadTopRatedMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>>

    fun loadPopularMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>>

    fun loadTrendingMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>>

    fun loadAllGenres(
        languageCode: String
    ): Flow<GenericState<List<Genre>>>

    fun search(
        query: String,
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>>

    fun loadMovieDetails(
        movieId: Int,
        languageCode: String
    ): Flow<GenericState<MovieDetails?>>

    suspend fun insertMovies(movies: List<MovieDetails>)

}