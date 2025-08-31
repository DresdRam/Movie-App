package sq.mayv.feature.movie_details

import app.cash.turbine.test
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import sq.mayv.core.testing.MockMoviesRepository
import sq.mayv.core.testing.TestingUtils
import sq.mayv.feature.movie_details.domain.LoadMovieDetailsUseCase
import sq.mayv.feature.movie_details.ui.MovieDetailsViewModel
import sq.mayv.feature.movie_details.ui.state.MovieDetailsUIState

@HiltAndroidTest
class MovieDetailsViewModelTest {

    private lateinit var repository: MockMoviesRepository
    private lateinit var movieDetailsUseCase: LoadMovieDetailsUseCase
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setup() {
        repository = MockMoviesRepository()
        movieDetailsUseCase = LoadMovieDetailsUseCase(repository = repository)
        viewModel = MovieDetailsViewModel(movieDetailsUseCase = movieDetailsUseCase)
    }

    @Test
    fun `movie details state is initially loading`() = runTest {
        TestCase.assertEquals(MovieDetailsUIState.Loading, viewModel.movieDetailsState.value)
    }

    @Test
    fun `state is success on successful request`() = runTest {
        val result = TestingUtils.moviesJsonAsItems()
        val movie = result.first()

        repository.setIsSuccessful(isSuccessful = true)
        repository.setMovies(movies = result)
        viewModel.getMovieDetails(movieId = movie.id)

        viewModel.movieDetailsState.test {
            val loadingEmission = awaitItem()
            assert(loadingEmission is MovieDetailsUIState.Loading)
            val successEmission = awaitItem()
            assert(successEmission is MovieDetailsUIState.Success)
            val successState = successEmission as MovieDetailsUIState.Success
            TestCase.assertEquals(movie, successState.movieDetails)
        }
    }
}