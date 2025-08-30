package sq.mayv.feature.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.design.component.MessageView
import sq.mayv.core.design.extension.shimmer
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.feature.home.R
import sq.mayv.feature.home.ui.state.MoviesUIState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (movieId: Int) -> Unit,
    onSearchClick: () -> Unit
) {
    val upcomingMoviesState by viewModel.upcomingMoviesState.collectAsState()
    val popularMoviesState by viewModel.popularMoviesState.collectAsState()
    val trendingMoviesState by viewModel.trendingMoviesState.collectAsState()
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

    LaunchedEffect(isConnected) {
        viewModel.getUpcomingMovies(pageIndex = 1)
        viewModel.getPopularMovies(pageIndex = 1)
        viewModel.getTrendingMovies(pageIndex = 1)
    }

    ScreenContent(
        modifier = modifier,
        upcomingMoviesState = upcomingMoviesState,
        popularMoviesState = popularMoviesState,
        trendingMoviesState = trendingMoviesState,
        onMovieClick = { movieId ->
            onMovieClick.invoke(movieId)
        },
        onSearchClick = onSearchClick
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    upcomingMoviesState: MoviesUIState,
    popularMoviesState: MoviesUIState,
    trendingMoviesState: MoviesUIState,
    onMovieClick: (movieId: Int) -> Unit,
    onSearchClick: () -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchView(
                onSearchClick = {
                    onSearchClick.invoke()
                }
            )

            TitleView(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 12.dp),
                title = stringResource(R.string.upcoming_movies)
            )
            MoviesAnimatedView(
                modifier = Modifier.padding(top = 6.dp),
                moviesState = upcomingMoviesState,
                onItemClick = { movieId ->
                    onMovieClick.invoke(movieId)
                }
            )

            TitleView(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp),
                title = stringResource(R.string.popular_movies)
            )
            MoviesAnimatedView(
                modifier = Modifier.padding(top = 6.dp),
                moviesState = popularMoviesState,
                onItemClick = { movieId ->
                    onMovieClick.invoke(movieId)
                }
            )

            TitleView(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp),
                title = stringResource(R.string.trending_movies)
            )
            MoviesAnimatedView(
                modifier = Modifier.padding(top = 6.dp),
                moviesState = trendingMoviesState,
                onItemClick = { movieId ->
                    onMovieClick.invoke(movieId)
                }
            )
        }
    }
}

@Composable
fun TitleView(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier
            .padding(horizontal = 15.dp),
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun MoviesAnimatedView(
    modifier: Modifier = Modifier,
    moviesState: MoviesUIState,
    onItemClick: (movieId: Int) -> Unit
) {
    AnimatedContent(
        modifier = modifier,
        targetState = moviesState,
        label = ""
    ) { state ->
        when (state) {
            MoviesUIState.Loading -> {
                MoviesRowShimmerView()
            }

            MoviesUIState.Empty -> {
                MessageView(
                    message = stringResource(R.string.try_connecting_to_the_internet),
                )
            }

            is MoviesUIState.Success -> {
                MoviesRowView(
                    movies = state.movies,
                    onItemClick = { movieId ->
                        onItemClick.invoke(movieId)
                    }
                )
            }

            is MoviesUIState.Error -> {
                MessageView(
                    message = when (state.errorCode) {
                        ErrorCode.UNSPECIFIED -> stringResource(R.string.something_went_wrong)
                        ErrorCode.DATABASE_ERROR -> stringResource(R.string.database_error)
                        else -> stringResource(R.string.server_error)
                    },
                )
            }
        }
    }
}

@Composable
fun MoviesRowShimmerView() {
    LazyRow {
        items(items = listOf(1, 2, 3, 4, 5)) { movie ->
            MoviesItemShimmerView()
        }
    }
}

@Composable
fun MoviesRowView(movies: List<MovieDetails>, onItemClick: (movieId: Int) -> Unit) {
    LazyRow {
        items(items = movies, key = { it.id }) { movie ->
            MoviesItemView(
                movie = movie,
                onItemClick = { movieId ->
                    onItemClick.invoke(movieId)
                }
            )
        }
    }
}

@Composable
fun MoviesItemShimmerView() {
    Card(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .width(140.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Column {

            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(210.dp)
                    .shimmer()
            )

            MovieName(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .padding(all = 6.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer(),
                name = ""
            )
        }
    }
}

@Composable
fun MoviesItemView(
    movie: MovieDetails,
    onItemClick: (movieId: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .width(140.dp)
            .wrapContentHeight()
            .clickable {
                onItemClick.invoke(movie.id)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Column {

            MoviePoster(
                modifier = Modifier
                    .width(140.dp)
                    .height(210.dp),
                imageURL = movie.posterPath
            )

            MovieName(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 6.dp),
                name = movie.title
            )
        }
    }
}

@Composable
fun MoviePoster(modifier: Modifier, imageURL: String?) {
    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(2f / 3f),
            model = imageURL,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MovieName(modifier: Modifier, name: String) {
    Text(
        modifier = modifier,
        text = name,
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun SearchView(onSearchClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(end = 15.dp, top = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SearchButton(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.CenterEnd),
            onClicked = {
                onSearchClick.invoke()
            }
        )
    }
}

@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    Button(
        modifier = modifier.size(46.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(5.dp),
        onClick = { onClicked.invoke() }
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = ""
        )
    }
}