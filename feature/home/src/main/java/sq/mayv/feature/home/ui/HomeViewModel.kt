package sq.mayv.feature.home.ui

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
import kotlinx.coroutines.flow.update
import sq.mayv.core.common.ConnectivityObserver
import sq.mayv.core.common.Source
import sq.mayv.feature.home.domain.LoadPopularMoviesUseCase
import sq.mayv.feature.home.domain.LoadTrendingMoviesUseCase
import sq.mayv.feature.home.domain.LoadUpcomingMoviesUseCase
import sq.mayv.feature.home.ui.state.MoviesUIState
import java.util.Locale

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val upcomingMoviesUseCase: LoadUpcomingMoviesUseCase,
    private val popularMoviesUseCase: LoadPopularMoviesUseCase,
    private val trendingMoviesUseCase: LoadTrendingMoviesUseCase,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val isConnected = connectivityObserver
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false
        )

    private val _upcomingMoviesState: MutableStateFlow<MoviesUIState> =
        MutableStateFlow(MoviesUIState.Loading)

    val upcomingMoviesState = _upcomingMoviesState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MoviesUIState.Loading
    )

    private val _popularMoviesState: MutableStateFlow<MoviesUIState> =
        MutableStateFlow(MoviesUIState.Loading)

    val popularMoviesState = _popularMoviesState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MoviesUIState.Loading
    )

    private val _trendingMoviesState: MutableStateFlow<MoviesUIState> =
        MutableStateFlow(MoviesUIState.Loading)

    val trendingMoviesState = _trendingMoviesState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MoviesUIState.Loading
    )

    fun getUpcomingMovies(pageIndex: Int) {
        upcomingMoviesUseCase(pageIndex = pageIndex, languageCode = Locale.getDefault().language)
            .onEach { newState ->
                _upcomingMoviesState.update { current ->
                    if (current is MoviesUIState.Success && current.source == Source.Remote) {
                        current
                    } else {
                        newState
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getPopularMovies(pageIndex: Int) {
        popularMoviesUseCase(pageIndex = pageIndex, languageCode = Locale.getDefault().language)
            .onEach { newState ->
                _popularMoviesState.update { current ->
                    if (current is MoviesUIState.Success && current.source == Source.Remote) {
                        current
                    } else {
                        newState
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getTrendingMovies(pageIndex: Int) {
        trendingMoviesUseCase(pageIndex = pageIndex, languageCode = Locale.getDefault().language)
            .onEach { newState ->
                _trendingMoviesState.update { current ->
                    if (current is MoviesUIState.Success && current.source == Source.Remote) {
                        current
                    } else {
                        newState
                    }
                }
            }.launchIn(viewModelScope)
    }
}