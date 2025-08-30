package sq.mayv.feature.movie_details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import sq.mayv.feature.movie_details.domain.LoadMovieDetailsUseCase
import sq.mayv.feature.movie_details.ui.state.MovieDetailsUIState
import java.util.Locale

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: LoadMovieDetailsUseCase,
) : ViewModel() {

    private val _movieDetailsState: MutableStateFlow<MovieDetailsUIState> =
        MutableStateFlow(MovieDetailsUIState.Loading)

    val movieDetailsState = _movieDetailsState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MovieDetailsUIState.Loading
    )

    fun getMovieDetails(movieId: Int) {
        movieDetailsUseCase(movieId = movieId, languageCode = Locale.getDefault().language)
            .onEach {
                _movieDetailsState.value = it
            }.launchIn(viewModelScope)
    }
}