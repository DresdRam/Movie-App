package sq.mayv.core.testing

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.core.common.Source
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.repository.IMoviesRepository

class MockMoviesRepository : IMoviesRepository {

    private val moviesFlow: MutableSharedFlow<List<MovieDetails>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val genresFlow: MutableSharedFlow<List<Genre>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private var shouldSuccess = true
    private var source: Source = Source.Remote
    private var error: ErrorCode = ErrorCode.UNSPECIFIED

    fun setMovies(movies: List<MovieDetails>) {
        this.moviesFlow.tryEmit(movies)
    }

    fun setGenres(genres: List<Genre>) {
        this.genresFlow.tryEmit(genres)
    }

    fun setSource(source: Source) {
        this.source = source
    }

    fun setIsSuccessful(isSuccessful: Boolean, errorCode: ErrorCode? = null) {
        this.shouldSuccess = isSuccessful
        this.error = errorCode ?: ErrorCode.UNSPECIFIED
    }

    override fun loadUpcomingMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> {
        return if (shouldSuccess)
            moviesFlow.map { movies -> GenericState.Success(data = movies, source = source) }
        else
            flow { emit(GenericState.Failure(errorCode = error)) }
    }

    override fun loadTopRatedMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> {
        return if (shouldSuccess)
            moviesFlow.map { movies -> GenericState.Success(data = movies, source = source) }
        else
            flow { emit(GenericState.Failure(errorCode = error)) }
    }

    override fun loadPopularMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> {
        return if (shouldSuccess)
            moviesFlow.map { movies -> GenericState.Success(data = movies, source = source) }
        else
            flow { emit(GenericState.Failure(errorCode = error)) }
    }

    override fun loadTrendingMovies(
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> {
        return if (shouldSuccess)
            moviesFlow.map { movies -> GenericState.Success(data = movies, source = source) }
        else
            flow { emit(GenericState.Failure(errorCode = error)) }
    }

    override fun loadAllGenres(languageCode: String): Flow<GenericState<List<Genre>>> {
        return if (shouldSuccess)
            genresFlow.map { genres -> GenericState.Success(data = genres, source = source) }
        else
            flow { emit(GenericState.Failure(errorCode = error)) }
    }

    override fun search(
        query: String,
        pageIndex: Int,
        languageCode: String
    ): Flow<GenericState<List<MovieDetails>>> {
        return if (shouldSuccess)
            moviesFlow.map { movies -> GenericState.Success(data = movies, source = source) }
        else
            flow { emit(GenericState.Failure(errorCode = error)) }
    }

    override fun loadMovieDetails(
        movieId: Int,
        languageCode: String
    ): Flow<GenericState<MovieDetails?>> {
        return if (shouldSuccess)
            moviesFlow.map { movies ->
                GenericState.Success(
                    data = movies.first(),
                    source = source
                )
            }
        else
            flow { emit(GenericState.Failure(errorCode = error)) }
    }

    override suspend fun insertMovies(movies: List<MovieDetails>) {
        // Do nothing
    }

}