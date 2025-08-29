package sq.mayv.feature.home.ui.state

import sq.mayv.core.common.ErrorCode

sealed interface MoviesUIState {

    object Loading: MoviesUIState

    object Empty: MoviesUIState

    data class Success(val movies: List<MovieDetailsUI>): MoviesUIState

    data class Error(val errorCode: ErrorCode): MoviesUIState
}