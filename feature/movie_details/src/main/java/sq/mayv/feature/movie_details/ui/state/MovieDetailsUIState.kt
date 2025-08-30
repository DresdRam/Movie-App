package sq.mayv.feature.movie_details.ui.state

import sq.mayv.core.common.ErrorCode
import sq.mayv.data.model.network.MovieDetails

sealed interface MovieDetailsUIState {

    object Loading : MovieDetailsUIState

    data class Success(val movieDetails: MovieDetails) : MovieDetailsUIState

    data class Error(val errorCode: ErrorCode) : MovieDetailsUIState
}