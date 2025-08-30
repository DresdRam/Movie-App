package sq.mayv.feature.movie_details.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(val movieId: Int)

fun NavGraphBuilder.movieDetailsScreen(onBackClicked: () -> Unit) {
    composable<MovieDetailsRoute> { backStackEntry ->
        val details = backStackEntry.toRoute<MovieDetailsRoute>()
        MovieDetailsScreen(
            movieId = details.movieId,
            onBackClicked = onBackClicked
        )
    }
}