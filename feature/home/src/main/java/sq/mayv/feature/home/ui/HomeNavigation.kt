package sq.mayv.feature.home.ui

import androidx.navigation.*
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavGraphBuilder.homeScreen() {
    composable<Home> { HomeScreen() }
}