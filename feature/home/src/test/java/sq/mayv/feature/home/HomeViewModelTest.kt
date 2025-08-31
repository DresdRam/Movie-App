package sq.mayv.feature.home

import app.cash.turbine.test
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import sq.mayv.core.common.ConnectivityObserver
import sq.mayv.core.common.Source
import sq.mayv.core.testing.MockConnectivityObserver
import sq.mayv.core.testing.MockMoviesRepository
import sq.mayv.core.testing.TestingUtils
import sq.mayv.feature.home.domain.LoadPopularMoviesUseCase
import sq.mayv.feature.home.domain.LoadTrendingMoviesUseCase
import sq.mayv.feature.home.domain.LoadUpcomingMoviesUseCase
import sq.mayv.feature.home.ui.HomeViewModel
import sq.mayv.feature.home.ui.state.MoviesUIState

@HiltAndroidTest
class HomeViewModelTest {
    private lateinit var repository: MockMoviesRepository
    private lateinit var loadUpcomingMoviesUseCase: LoadUpcomingMoviesUseCase
    private lateinit var loadPopularMoviesUseCase: LoadPopularMoviesUseCase
    private lateinit var loadTrendingMoviesUseCase: LoadTrendingMoviesUseCase

    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        repository = MockMoviesRepository()
        loadUpcomingMoviesUseCase = LoadUpcomingMoviesUseCase(repository = repository)
        loadPopularMoviesUseCase = LoadPopularMoviesUseCase(repository = repository)
        loadTrendingMoviesUseCase = LoadTrendingMoviesUseCase(repository = repository)
        connectivityObserver = MockConnectivityObserver()
        viewModel = HomeViewModel(
            upcomingMoviesUseCase = loadUpcomingMoviesUseCase,
            popularMoviesUseCase = loadPopularMoviesUseCase,
            trendingMoviesUseCase = loadTrendingMoviesUseCase,
            connectivityObserver = connectivityObserver
        )
    }

    @Test
    fun `upcoming movies state is initially loading`() = runTest {
        TestCase.assertEquals(MoviesUIState.Loading, viewModel.upcomingMoviesState.value)
    }

    @Test
    fun `popular movies state is initially loading`() = runTest {
        TestCase.assertEquals(MoviesUIState.Loading, viewModel.popularMoviesState.value)
    }

    @Test
    fun `trending movies state is initially loading`() = runTest {
        TestCase.assertEquals(MoviesUIState.Loading, viewModel.trendingMoviesState.value)
    }

    @Test
    fun `state is success on successful request`() = runTest {
        val result = TestingUtils.moviesJsonAsItems()

        repository.setIsSuccessful(isSuccessful = true)
        repository.setMovies(movies = result)
        viewModel.getUpcomingMovies(pageIndex = 1)

        viewModel.upcomingMoviesState.test {
            val loadingEmission = awaitItem()
            assert(loadingEmission is MoviesUIState.Loading)
            val successEmission = awaitItem()
            assert(successEmission is MoviesUIState.Success)
            val successState = successEmission as MoviesUIState.Success
            TestCase.assertEquals(result, successState.movies)
        }
    }

    @Test
    fun `state doesn't change if source was remote then became local`() = runTest {
        val result = TestingUtils.moviesJsonAsItems()

        repository.setIsSuccessful(isSuccessful = true)
        repository.setMovies(movies = result)
        repository.setSource(source = Source.Remote)
        viewModel.getUpcomingMovies(pageIndex = 1)

        viewModel.upcomingMoviesState.test {
            val loadingEmission = awaitItem()
            assert(loadingEmission is MoviesUIState.Loading)
            val successEmissionRemote = awaitItem()
            assert(successEmissionRemote is MoviesUIState.Success)
            val successStateRemote = successEmissionRemote as MoviesUIState.Success
            assert(successStateRemote.source == Source.Remote)
        }

        repository.setSource(source = Source.Local)
        viewModel.getUpcomingMovies(pageIndex = 1)

        viewModel.upcomingMoviesState.test {
            val successEmissionRemote = awaitItem()
            assert(successEmissionRemote is MoviesUIState.Success)
            val successStateRemote = successEmissionRemote as MoviesUIState.Success
            assert(successStateRemote.source == Source.Remote)
        }
    }
}