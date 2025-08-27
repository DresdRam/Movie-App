package sq.mayv.data.local.datasource

import sq.mayv.core.common.GenericState
import sq.mayv.data.model.movies.Language
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.model.network.PagingResponse

interface ILocalDataSource {

    suspend fun loadUpcomingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<PagingResponse<MovieDetails>>

    suspend fun loadPopularMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<PagingResponse<MovieDetails>>

    suspend fun loadTrendingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<PagingResponse<MovieDetails>>

    suspend fun loadAllGenres(
        language: Language
    ): GenericState<List<Genre>>

    suspend fun search(
        query: String,
        pageIndex: Int,
        language: Language
    ): GenericState<PagingResponse<MovieDetails>>

    suspend fun loadMovieDetails(
        movieId: Int,
        language: Language
    ): GenericState<MovieDetails>

    suspend fun insertMovies(movies: List<MovieDetails>)

}