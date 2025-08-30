package sq.mayv.feature.home.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavGraphBuilder.homeScreen(
    onMovieClick: (movieId: Int) -> Unit,
    onSearchClick: () -> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            onMovieClick = onMovieClick,
            onSearchClick = onSearchClick
        )
    }
}