package sq.mayv.data.repository

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.data.local.datasource.ILocalDataSource
import sq.mayv.core.common.Language
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.remote.datasource.IRemoteDataSource

class MoviesRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource
) : IMoviesRepository {

    override fun loadUpcomingMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val localState =
                localDataSource.getUpcomingMovies(
                    pageIndex = pageIndex,
                    language = Language.from(code = languageCode)
                )

            emit(localState)

            val remoteState =
                remoteDataSource.loadUpcomingMovies(
                    pageIndex = pageIndex,
                    language = Language.from(code = languageCode)
                )

            if (remoteState is GenericState.Success) {
                localDataSource.insertMovies(list = remoteState.data)
                emit(remoteState)
            }
        } catch (e: Exception) {
            emit(GenericState.Failure(errorCode = ErrorCode.SERVER_ERROR))
        }
    }

    override fun loadTopRatedMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val remoteState =
                remoteDataSource.loadTopRatedMovies(
                    pageIndex = pageIndex,
                    language = Language.from(code = languageCode)
                )

            if (remoteState is GenericState.Success) {
                localDataSource.insertMovies(list = remoteState.data)
                emit(remoteState)
            }
        } catch (e: Exception) {
            val localState =
                localDataSource.getTopRatedMovies(
                    pageIndex = pageIndex,
                    language = Language.from(code = languageCode)
                )
            emit(localState)
        }
    }

    override fun loadPopularMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val localState =
                localDataSource.getPopularMovies(
                    pageIndex = pageIndex,
                    language = Language.from(code = languageCode)
                )

            emit(localState)

            val remoteState =
                remoteDataSource.loadPopularMovies(
                    pageIndex = pageIndex,
                    language = Language.from(code = languageCode)
                )

            if (remoteState is GenericState.Success) {
                localDataSource.insertMovies(list = remoteState.data)
                emit(remoteState)
            }
        } catch (e: Exception) {
            emit(GenericState.Failure(errorCode = ErrorCode.SERVER_ERROR))
        }
    }

    override fun loadTrendingMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val localState =
                localDataSource.getTrendingMovies(
                    pageIndex = pageIndex,
                    language = Language.from(code = languageCode)
                )

            emit(localState)

            val remoteState =
                remoteDataSource.loadTrendingMovies(
                    pageIndex = pageIndex,
                    language = Language.from(code = languageCode)
                )

            if (remoteState is GenericState.Success) {
                localDataSource.insertMovies(list = remoteState.data)
                emit(remoteState)
            }
        } catch (e: Exception) {
            emit(GenericState.Failure(errorCode = ErrorCode.SERVER_ERROR))
        }
    }

    override fun loadAllGenres(languageCode: String): Flow<GenericState<List<Genre>>> = flow {
        try {
            val remoteState =
                remoteDataSource.loadAllGenres(language = Language.from(code = languageCode))
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
                localDataSource.loadAllGenres(language = Language.from(code = languageCode))
            emit(localState)
        }
    }

    override fun search(
        query: String,
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> = flow {
        try {
            val remoteState = remoteDataSource.search(
                query = query,
                pageIndex = pageIndex,
                language = Language.from(code = languageCode)
            )
            emit(remoteState)
        } catch (e: Exception) {
            emit(GenericState.Failure(errorCode = ErrorCode.SERVER_ERROR))
        }
    }

    override fun loadMovieDetails(
        movieId: Int,
        languageCode: String
    ): Flow<GenericState<MovieDetails?>> = flow {
        try {
            val remoteState =
                remoteDataSource.loadMovieDetails(
                    movieId = movieId,
                    language = Language.from(code = languageCode)
                )
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
                localDataSource.getMovieDetails(
                    movieId = movieId,
                    language = Language.from(code = languageCode)
                )
            emit(localState)
        }
    }

    override suspend fun insertMovies(movies: List<MovieDetails>) {
        localDataSource.insertMovies(list = movies)
    }

}