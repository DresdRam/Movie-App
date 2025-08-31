package sq.mayv.feature.home.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.data.repository.IMoviesRepository
import sq.mayv.feature.home.ui.state.MoviesUIState
import javax.inject.Inject

class LoadTrendingMoviesUseCase @Inject constructor(
    private val repository: IMoviesRepository
) {
    operator fun invoke(pageIndex: Int, languageCode: String): Flow<MoviesUIState> =
        repository.loadTrendingMovies(pageIndex = pageIndex, languageCode = languageCode).map {
            when (it) {
                is GenericState.Success -> {
                    if (it.data.isEmpty()) {
                        MoviesUIState.Empty
                    } else {
                        MoviesUIState.Success(movies = it.data, source = it.source)
                    }
                }

                is GenericState.Failure -> {
                    MoviesUIState.Error(errorCode = it.errorCode)
                }
            }
        }.catch {
            emit(MoviesUIState.Error(errorCode = ErrorCode.UNSPECIFIED))
        }.onStart {
            emit(MoviesUIState.Loading)
        }
}