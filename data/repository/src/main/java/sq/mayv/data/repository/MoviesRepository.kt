package sq.mayv.data.repository

import kotlinx.coroutines.flow.Flow
import sq.mayv.core.common.GenericState
import sq.mayv.data.model.movies.Language
import sq.mayv.data.local.datasource.ILocalDataSource
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.model.network.PagingResponse
import sq.mayv.data.remote.datasource.IRemoteDataSource

class MoviesRepository(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource
) : IMoviesRepository {

    override fun loadUpcomingMovies(
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<PagingResponse<MovieDetails>>> {
        TODO("Not yet implemented")
    }

    override fun loadPopularMovies(
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<PagingResponse<MovieDetails>>> {
        TODO("Not yet implemented")
    }

    override fun loadTrendingMovies(
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<PagingResponse<MovieDetails>>> {
        TODO("Not yet implemented")
    }

    override fun loadAllGenres(language: Language): Flow<GenericState<List<Genre>>> {
        TODO("Not yet implemented")
    }

    override fun search(
        query: String,
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<PagingResponse<MovieDetails>>> {
        TODO("Not yet implemented")
    }

    override fun loadMovieDetails(
        movieId: Int,
        language: Language
    ): Flow<GenericState<MovieDetails>> {
        TODO("Not yet implemented")
    }

    override fun insertMovies(movies: List<MovieDetails>) {
        TODO("Not yet implemented")
    }

}