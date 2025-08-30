package sq.mayv.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import sq.mayv.feature.home.ui.HomeRoute
import sq.mayv.feature.home.ui.homeScreen
import sq.mayv.feature.movie_details.ui.MovieDetailsRoute
import sq.mayv.feature.movie_details.ui.movieDetailsScreen
import sq.mayv.feature.search.ui.SearchRoute
import sq.mayv.feature.search.ui.searchScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        homeScreen(
            onMovieClick = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId = movieId))
            },
            onSearchClick = {
                navController.navigate(SearchRoute)
            }
        )
        movieDetailsScreen(
            onBackClicked = {
                navController.navigateUp()
            }
        )
        searchScreen(
            onMovieClick = { movieId ->
                navController.navigate(MovieDetailsRoute(movieId = movieId))
            }
        )
    }
}