package sq.mayv.data.local.datasource

import sq.mayv.core.common.GenericState
import sq.mayv.data.model.movies.Language
import sq.mayv.data.local.dao.MoviesDao
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.model.network.PagingResponse
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao
) : ILocalDataSource {

    override suspend fun loadUpcomingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<PagingResponse<MovieDetails>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadPopularMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<PagingResponse<MovieDetails>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadTrendingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<PagingResponse<MovieDetails>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadAllGenres(language: Language): GenericState<List<Genre>> {
        TODO("Not yet implemented")
    }

    override suspend fun search(
        query: String,
        pageIndex: Int,
        language: Language
    ): GenericState<PagingResponse<MovieDetails>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadMovieDetails(
        movieId: Int,
        language: Language
    ): GenericState<MovieDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovies(movies: List<MovieDetails>) {
        TODO("Not yet implemented")
    }

}