package sq.mayv.feature.movie_details.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import sq.mayv.core.common.extension.toDateMillis
import sq.mayv.core.common.extension.toDateString
import sq.mayv.core.design.component.MovieDurationView
import sq.mayv.core.design.component.MovieGenresView
import sq.mayv.core.design.component.MovieRatingView
import sq.mayv.core.design.component.MovieReleaseDateView
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.feature.movie_details.R
import sq.mayv.feature.movie_details.ui.state.MovieDetailsUIState
import java.util.Locale

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val movieDetailsState by viewModel.movieDetailsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(movieId = movieId)
    }

    ScreenContent(
        modifier = modifier,
        movieDetailsState = movieDetailsState,
        onBackClicked = onBackClicked
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    movieDetailsState: MovieDetailsUIState,
    onBackClicked: () -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AnimatedContent(
                targetState = movieDetailsState,
                label = ""
            ) { state ->
                when (state) {
                    MovieDetailsUIState.Loading -> {

                    }

                    is MovieDetailsUIState.Success -> {
                        MovieDetailsSurface(
                            movieDetails = state.movieDetails,
                            onBackClicked = onBackClicked
                        )
                    }

                    is MovieDetailsUIState.Error -> {

                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailsSurface(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails,
    onBackClicked: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        GradientBackgroundView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f),
            imageUrl = movieDetails.posterPath
        )

        MovieDetailsContent(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            movieDetails = movieDetails,
            onBackClicked = onBackClicked
        )
    }
}

@Composable
fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails,
    onBackClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarView(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            movieName = movieDetails.title,
            onBackClicked = onBackClicked
        )

        MoviePoster(
            modifier = Modifier
                .padding(top = 20.dp)
                .width(220.dp)
                .wrapContentHeight(),
            imageURL = movieDetails.posterPath
        )

        MovieExtraInfoView(
            modifier = Modifier.padding(top = 20.dp),
            movieDetails = movieDetails
        )
    }
}

@Composable
fun MovieExtraInfoView(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            MovieReleaseDateView(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                releaseDate = movieDetails.releaseDate.toDateMillis().toDateString(
                    pattern = "MMMM yyyy",
                    locale = Locale.getDefault()
                )
            )
            Text(
                text = " | ",
                fontSize = 14.sp,
                color = colorResource(R.color.dark_gray)
            )
            MovieDurationView(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                duration = movieDetails.runtime
            )
        }

        MovieGenresView(
            modifier = Modifier.padding(top = 10.dp),
            genres = movieDetails.genres?.map { it.name } ?: emptyList()
        )

        MovieRatingView(
            modifier = Modifier.padding(top = 10.dp),
            rating = movieDetails.voteAverage
        )

        TitleView(
            modifier = Modifier
                .padding(top = 15.dp, start = 15.dp)
                .align(Alignment.Start),
            title = stringResource(R.string.overview)
        )

        ExpandableText(
            modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp),
            text = movieDetails.overview
        )
    }
}

@Composable
fun TitleView(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
    )
}

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    minimizedMaxLines: Int = 3
) {
    var expanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .animateContentSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                if (!expanded) {
                    isOverflowing = textLayoutResult.hasVisualOverflow
                }
            },
            style = MaterialTheme.typography.bodyMedium
        )

        if (isOverflowing || expanded) {
            TextButton(
                onClick = { expanded = !expanded },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = if (expanded) stringResource(R.string.read_less) else stringResource(R.string.read_more),
                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Composable
fun MoviePoster(modifier: Modifier, imageURL: String?) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
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
fun TopBarView(
    modifier: Modifier = Modifier,
    movieName: String,
    onBackClicked: () -> Unit
) {
    Box(modifier = modifier) {

        BackButtonView(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp),
            onClicked = onBackClicked
        )

        MovieNameView(
            modifier = Modifier.align(Alignment.Center),
            title = movieName
        )
    }
}

@Composable
fun MovieNameView(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier
            .padding(horizontal = 75.dp),
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun BackButtonView(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    IconButton(
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent),
        onClick = { onClicked.invoke() }
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = ""
        )
    }
}

@Composable
fun GradientBackgroundView(
    modifier: Modifier = Modifier,
    imageUrl: String?
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x801F1D2B),
                            Color(0xFF1F1D2B)
                        )
                    )
                )
        )
    }
}