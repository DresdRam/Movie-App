package sq.mayv.feature.search.ui

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
import sq.mayv.core.common.ConnectivityObserver
import sq.mayv.feature.search.domain.LoadTopRatedMoviesUseCase
import sq.mayv.feature.search.domain.SearchMoviesUseCase
import sq.mayv.feature.search.ui.state.SearchUIState
import java.util.Locale

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val topRatedMoviesUseCase: LoadTopRatedMoviesUseCase,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val isConnected = connectivityObserver
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false
        )

    private val _moviesUiState: MutableStateFlow<SearchUIState> =
        MutableStateFlow(SearchUIState.Loading)

    val moviesUiState = _moviesUiState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SearchUIState.Loading
    )

    fun loadTopRatedMovies(pageIndex: Int) {
        topRatedMoviesUseCase(
            pageIndex = pageIndex,
            languageCode = Locale.getDefault().language
        ).onEach {
            _moviesUiState.value = it
        }.launchIn(viewModelScope)
    }

    fun search(query: String, pageIndex: Int) {
        searchMoviesUseCase(
            query = query,
            pageIndex = pageIndex,
            languageCode = Locale.getDefault().language
        ).onEach {
            _moviesUiState.value = it
        }.launchIn(viewModelScope)
    }
}