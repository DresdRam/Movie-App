package sq.mayv.feature.home.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.data.repository.MoviesRepository
import sq.mayv.feature.home.mapper.MoviesModelUIMapper
import sq.mayv.feature.home.ui.state.MoviesUIState
import javax.inject.Inject

class LoadUpcomingMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val moviesMapper: MoviesModelUIMapper
) {
    operator fun invoke(pageIndex: Int, languageCode: String): Flow<MoviesUIState> =
        repository.loadUpcomingMovies(pageIndex = pageIndex, languageCode = languageCode).map {
            when (it) {
                is GenericState.Success -> {
                    MoviesUIState.Success(movies = moviesMapper.mapToUI(list = it.data))
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