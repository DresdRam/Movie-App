package sq.mayv.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import sq.mayv.feature.home.ui.state.MoviesUIState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val upcomingMoviesState by viewModel.upcomingMoviesState.collectAsState()
    val popularMoviesState by viewModel.popularMoviesState.collectAsState()
    val trendingMoviesState by viewModel.trendingMoviesState.collectAsState()

    ScreenContent(
        modifier = modifier,
        upcomingMoviesState = upcomingMoviesState,
        popularMoviesState = popularMoviesState,
        trendingMoviesState = trendingMoviesState
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    upcomingMoviesState: MoviesUIState,
    popularMoviesState: MoviesUIState,
    trendingMoviesState: MoviesUIState
) {

}