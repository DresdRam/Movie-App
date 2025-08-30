package sq.mayv.feature.search.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.data.repository.MoviesRepository
import sq.mayv.feature.search.ui.state.SearchUIState
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(
        query: String,
        pageIndex: Int,
        languageCode: String
    ): Flow<SearchUIState> =
        repository.search(query = query, pageIndex = pageIndex, languageCode = languageCode).map {
            when (it) {
                is GenericState.Success -> {
                    if (it.data.isEmpty()) {
                        SearchUIState.Empty
                    } else {
                        SearchUIState.Success(movies = it.data)
                    }
                }

                is GenericState.Failure -> {
                    SearchUIState.Error(errorCode = it.errorCode)
                }
            }
        }.catch {
            emit(SearchUIState.Error(errorCode = ErrorCode.UNSPECIFIED))
        }.onStart {
            emit(SearchUIState.Loading)
        }
}