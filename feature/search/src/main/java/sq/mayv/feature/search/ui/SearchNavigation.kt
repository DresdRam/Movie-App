package sq.mayv.feature.search.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SearchRoute

fun NavGraphBuilder.searchScreen(
    onMovieClick: (movieId: Int) -> Unit
) {
    composable<SearchRoute> {
        SearchScreen(
            onMovieClick = onMovieClick
        )
    }
}