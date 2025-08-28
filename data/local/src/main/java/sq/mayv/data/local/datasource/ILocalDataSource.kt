package sq.mayv.data.local.datasource

import sq.mayv.core.common.GenericState
import sq.mayv.data.model.movies.Language
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails

interface ILocalDataSource {

    suspend fun getUpcomingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>>

    suspend fun getPopularMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>>

    suspend fun getTrendingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>>

    suspend fun loadAllGenres(
        language: Language
    ): GenericState<List<Genre>>

    suspend fun getMovieDetails(
        movieId: Int,
        language: Language
    ): GenericState<MovieDetails?>

    suspend fun insertGenres(list: List<Genre>)

    suspend fun insertMovies(list: List<MovieDetails>)

}