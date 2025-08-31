package sq.mayv.data.remote.datasource

import sq.mayv.core.common.GenericState
import sq.mayv.core.common.Language
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails

interface IRemoteDataSource {

    suspend fun loadUpcomingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>>

    suspend fun loadTopRatedMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>>

    suspend fun loadPopularMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>>

    suspend fun loadTrendingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>>

    suspend fun loadAllGenres(
        language: Language
    ): GenericState<List<Genre>>

    suspend fun search(
        query: String,
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>>

    suspend fun loadMovieDetails(
        movieId: Int,
        language: Language
    ): GenericState<MovieDetails?>

}