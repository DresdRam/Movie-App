package sq.mayv.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.data.local.datasource.ILocalDataSource
import sq.mayv.data.model.movies.Language
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.remote.datasource.IRemoteDataSource

class MoviesRepository(
    private val localDataSource: ILocalDataSource, private val remoteDataSource: IRemoteDataSource
) : IMoviesRepository {

    override fun loadUpcomingMovies(
        pageIndex: Int, language: Language
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val remoteState =
                remoteDataSource.loadUpcomingMovies(pageIndex = pageIndex, language = language)
            when (remoteState) {
                is GenericState.Success -> {
                    localDataSource.insertMovies(list = remoteState.data)
                    emit(remoteState)
                }

                is GenericState.Failure -> {
                    emit(remoteState)
                }
            }
        } catch (e: Exception) {
            val localState =
                localDataSource.getUpcomingMovies(pageIndex = pageIndex, language = language)
            emit(localState)
        }
    }

    override fun loadPopularMovies(
        pageIndex: Int, language: Language
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val remoteState =
                remoteDataSource.loadPopularMovies(pageIndex = pageIndex, language = language)
            when (remoteState) {
                is GenericState.Success -> {
                    localDataSource.insertMovies(list = remoteState.data)
                    emit(remoteState)
                }

                is GenericState.Failure -> {
                    emit(remoteState)
                }
            }
        } catch (e: Exception) {
            val localState =
                localDataSource.getPopularMovies(pageIndex = pageIndex, language = language)
            emit(localState)
        }
    }

    override fun loadTrendingMovies(
        pageIndex: Int, language: Language
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val remoteState =
                remoteDataSource.loadTrendingMovies(pageIndex = pageIndex, language = language)
            when (remoteState) {
                is GenericState.Success -> {
                    localDataSource.insertMovies(list = remoteState.data)
                    emit(remoteState)
                }

                is GenericState.Failure -> {
                    emit(remoteState)
                }
            }
        } catch (e: Exception) {
            val localState =
                localDataSource.getTrendingMovies(pageIndex = pageIndex, language = language)
            emit(localState)
        }
    }

    override fun loadAllGenres(language: Language): Flow<GenericState<List<Genre>>> = flow {
        try {
            val remoteState =
                remoteDataSource.loadAllGenres(language = language)
            when (remoteState) {
                is GenericState.Success -> {
                    localDataSource.insertGenres(list = remoteState.data)
                    emit(remoteState)
                }

                is GenericState.Failure -> {
                    emit(remoteState)
                }
            }
        } catch (e: Exception) {
            val localState =
                localDataSource.loadAllGenres(language = language)
            emit(localState)
        }
    }

    override fun search(
        query: String,
        pageIndex: Int,
        language: Language
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val remoteState = remoteDataSource.search(
                query = query,
                pageIndex = pageIndex,
                language = language
            )
            emit(remoteState)
        } catch (e: Exception) {
            emit(GenericState.Failure(errorCode = ErrorCode.SERVER_ERROR))
        }
    }

    override fun loadMovieDetails(
        movieId: Int,
        language: Language
    ): Flow<GenericState<MovieDetails?>> = flow {
        try {
            val remoteState =
                remoteDataSource.loadMovieDetails(movieId = movieId, language = language)
            when (remoteState) {
                is GenericState.Success -> {
                    remoteState.data?.let {
                        localDataSource.insertMovies(list = listOf(it))
                    }
                    emit(remoteState)
                }

                is GenericState.Failure -> {
                    emit(remoteState)
                }
            }
        } catch (e: Exception) {
            val localState =
                localDataSource.getMovieDetails(movieId = movieId, language = language)
            emit(localState)
        }
    }

    override suspend fun insertMovies(movies: List<MovieDetails>) {
        localDataSource.insertMovies(list = movies)
    }

}