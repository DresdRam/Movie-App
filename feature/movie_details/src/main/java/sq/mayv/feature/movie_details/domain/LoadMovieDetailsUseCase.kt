package sq.mayv.feature.movie_details.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.data.repository.MoviesRepository
import sq.mayv.feature.movie_details.ui.state.MovieDetailsUIState
import javax.inject.Inject

class LoadMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: Int, languageCode: String): Flow<MovieDetailsUIState> =
        repository.loadMovieDetails(movieId = movieId, languageCode = languageCode).map {
            when (it) {
                is GenericState.Success -> {
                    it.data?.let { movieDetails ->
                        MovieDetailsUIState.Success(movieDetails = movieDetails)
                    } ?: run {
                        MovieDetailsUIState.Error(errorCode = ErrorCode.UNSPECIFIED)
                    }
                }

                is GenericState.Failure -> {
                    MovieDetailsUIState.Error(errorCode = it.errorCode)
                }
            }
        }.catch {
            emit(MovieDetailsUIState.Error(errorCode = ErrorCode.UNSPECIFIED))
        }.onStart {
            emit(MovieDetailsUIState.Loading)
        }
}