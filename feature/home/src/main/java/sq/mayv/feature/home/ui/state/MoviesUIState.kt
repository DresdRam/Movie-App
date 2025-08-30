package sq.mayv.feature.home.ui.state

import sq.mayv.core.common.ErrorCode
import sq.mayv.data.model.network.MovieDetails

sealed interface MoviesUIState {

    object Loading : MoviesUIState

    object Empty : MoviesUIState

    data class Success(val movies: List<MovieDetails>) : MoviesUIState

    data class Error(val errorCode: ErrorCode) : MoviesUIState
}