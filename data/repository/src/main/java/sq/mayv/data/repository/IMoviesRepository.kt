package sq.mayv.data.repository

import kotlinx.coroutines.flow.Flow
import sq.mayv.core.common.GenericState
import sq.mayv.data.model.movies.Language
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.model.network.PagingResponse

interface IMoviesRepository {

    fun loadUpcomingMovies(
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<PagingResponse<MovieDetails>>>

    fun loadPopularMovies(
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<PagingResponse<MovieDetails>>>

    fun loadTrendingMovies(
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<PagingResponse<MovieDetails>>>

    fun loadAllGenres(
        language: Language
    ): Flow<GenericState<List<Genre>>>

    fun search(
        query: String,
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<PagingResponse<MovieDetails>>>

    fun loadMovieDetails(
        movieId: Int,
        language: Language
    ): Flow<GenericState<MovieDetails>>

    fun insertMovies(movies: List<MovieDetails>)

}