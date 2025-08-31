package sq.mayv.feature.search.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import sq.mayv.core.common.extension.toDateMillis
import sq.mayv.core.common.extension.toDateString
import sq.mayv.core.design.component.MessageView
import sq.mayv.core.design.component.MovieRatingView
import sq.mayv.core.design.component.MovieReleaseDateView
import sq.mayv.core.design.extension.shimmer
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.feature.search.R
import sq.mayv.feature.search.ui.state.SearchUIState
import java.util.Locale

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (movieId: Int) -> Unit
) {
    val searchState by viewModel.moviesUiState.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()
    var showConnectivityUi by remember { mutableStateOf(!isConnected) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(isConnected) {
        if (isConnected) {
            if (searchQuery.isEmpty()) {
                viewModel.loadTopRatedMovies(pageIndex = 1)
            } else {
                viewModel.search(query = searchQuery, pageIndex = 1)
            }
            showConnectivityUi = false
        }
    }

    ScreenContent(
        modifier = modifier,
        showConnectivityUi = showConnectivityUi,
        searchQuery = searchQuery,
        searchState = searchState,
        onMovieClick = { movieId ->
            onMovieClick.invoke(movieId)
        },
        onSearchQueryChanged = { query ->
            searchQuery = query
            if (isConnected) {
                viewModel.search(query = query, pageIndex = 1)
            } else {
                showConnectivityUi = true
            }
        }
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    searchState: SearchUIState,
    onMovieClick: (movieId: Int) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    showConnectivityUi: Boolean,
    searchQuery: String
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            SearchTextField(
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                query = searchQuery,
                onValueChange = { query ->
                    onSearchQueryChanged.invoke(query)
                }
            )

            if (showConnectivityUi) {
                Box(modifier = Modifier.fillMaxSize()) {
                    MessageView(
                        modifier = Modifier.align(Alignment.Center),
                        message = stringResource(R.string.no_internet_connection)
                    )
                }
            } else {
                SearchItemsView(
                    modifier = Modifier.padding(top = 20.dp),
                    searchState = searchState,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun SearchItemsView(
    modifier: Modifier = Modifier,
    searchState: SearchUIState,
    onMovieClick: (movieId: Int) -> Unit
) {
    AnimatedContent(
        modifier = modifier,
        targetState = searchState,
        label = ""
    ) { state ->
        when (state) {
            SearchUIState.Loading -> {
                MoviesColumnShimmerView()
            }

            SearchUIState.Empty -> {
                EmptySearchResultsView()
            }

            is SearchUIState.Success -> {
                MoviesColumnView(
                    movies = state.movies,
                    onItemClick = { movieId ->
                        onMovieClick.invoke(movieId)
                    }
                )
            }

            is SearchUIState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    MessageView(
                        modifier = Modifier.align(Alignment.Center),
                        message = stringResource(R.string.something_went_wrong)
                    )
                }
            }
        }
    }
}

@Composable
fun MoviesColumnShimmerView() {
    LazyColumn {
        items(items = listOf(1, 2, 3, 4, 5)) { movie ->
            MoviesItemShimmerView()
        }
    }
}

@Composable
fun MoviesColumnView(movies: List<MovieDetails>, onItemClick: (movieId: Int) -> Unit) {
    LazyColumn {
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
            .padding(top = 8.dp, start = 6.dp, end = 6.dp)
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(10.dp))
                    .shimmer()
            )

            MovieInfoShimmerView(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 6.dp)
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
            .padding(top = 8.dp, start = 6.dp, end = 6.dp)
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onItemClick.invoke(movie.id)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxSize()) {
                MoviePoster(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .aspectRatio(2f / 3f),
                    imageURL = movie.posterPath
                )

                MovieInfoView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 6.dp),
                    movie = movie
                )
            }

            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center
            ) {
                MovieRatingView(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    rating = movie.voteAverage
                )
            }
        }
    }
}

@Composable
fun MoviePoster(modifier: Modifier, imageURL: String?) {
    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageURL,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MovieInfoShimmerView(modifier: Modifier) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(35.dp)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .shimmer()
        )

        Box(
            modifier = Modifier
                .width(60.dp)
                .height(25.dp)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .shimmer()
        )

        Box(
            modifier = Modifier
                .width(90.dp)
                .height(45.dp)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .shimmer()
        )
    }
}

@Composable
fun MovieInfoView(modifier: Modifier, movie: MovieDetails) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            text = movie.title.ifEmpty { movie.originalTitle },
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )

        MovieReleaseDateView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            releaseDate = movie.releaseDate.toDateMillis().toDateString(
                pattern = "yyyy MMMM",
                locale = Locale.getDefault()
            )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = movie.overview,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun SearchTextField(
    query: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = modifier,
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                singleLine = true,
                value = query,
                onValueChange = {
                    onValueChange.invoke(it)
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                placeholder = { Text(text = "Search") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                    }
                )
            )

            TextButton(
                modifier = Modifier.padding(horizontal = 10.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onValueChange("")
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun EmptySearchResultsView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_empty_search_results),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp),
            tint = Color.Unspecified
        )

        Text(
            text = stringResource(R.string.no_results_found),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.no_results_found_message),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Color.Gray
            ),
            textAlign = TextAlign.Center
        )
    }
}