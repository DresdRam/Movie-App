package sq.mayv.feature.search

import app.cash.turbine.test
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import sq.mayv.core.common.ConnectivityObserver
import sq.mayv.core.testing.MockConnectivityObserver
import sq.mayv.core.testing.MockMoviesRepository
import sq.mayv.core.testing.TestingUtils
import sq.mayv.feature.search.domain.LoadTopRatedMoviesUseCase
import sq.mayv.feature.search.domain.SearchMoviesUseCase
import sq.mayv.feature.search.ui.SearchViewModel
import sq.mayv.feature.search.ui.state.SearchUIState

@HiltAndroidTest
class SearchViewModelTest {

    private lateinit var repository: MockMoviesRepository
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase
    private lateinit var topRatedMoviesUseCase: LoadTopRatedMoviesUseCase

    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        repository = MockMoviesRepository()
        searchMoviesUseCase = SearchMoviesUseCase(repository = repository)
        topRatedMoviesUseCase = LoadTopRatedMoviesUseCase(repository = repository)
        connectivityObserver = MockConnectivityObserver()
        viewModel = SearchViewModel(
            searchMoviesUseCase = searchMoviesUseCase,
            topRatedMoviesUseCase = topRatedMoviesUseCase,
            connectivityObserver = connectivityObserver
        )
    }

    @Test
    fun `search state is initially loading`() = runTest {
        TestCase.assertEquals(SearchUIState.Loading, viewModel.moviesUiState.value)
    }

    @Test
    fun `state is success on successful request`() = runTest {
        val spiderManMovie = TestingUtils.spiderManMovieAsItem()

        repository.setIsSuccessful(isSuccessful = true)
        repository.setMovies(movies = listOf(spiderManMovie))
        viewModel.search(query = spiderManMovie.title, pageIndex = 1)

        viewModel.moviesUiState.test {
            val loadingEmission = awaitItem()
            assert(loadingEmission is SearchUIState.Loading)
            val successEmission = awaitItem()
            assert(successEmission is SearchUIState.Success)
            val successState = successEmission as SearchUIState.Success
            TestCase.assertEquals(spiderManMovie, successState.movies.first())
        }
    }
}