package sq.mayv.feature.search.ui.state

import sq.mayv.core.common.ErrorCode
import sq.mayv.data.model.network.MovieDetails

sealed interface SearchUIState {

    object Loading : SearchUIState

    object Empty : SearchUIState

    data class Success(val movies: List<MovieDetails>) : SearchUIState

    data class Error(val errorCode: ErrorCode) : SearchUIState
}