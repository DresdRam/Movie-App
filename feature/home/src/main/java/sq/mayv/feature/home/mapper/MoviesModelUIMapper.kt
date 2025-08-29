package sq.mayv.feature.home.mapper

import sq.mayv.data.model.network.MovieDetails
import sq.mayv.feature.home.ui.state.MovieDetailsUI

object MoviesModelUIMapper {

    fun mapToUI(list: List<MovieDetails>): List<MovieDetailsUI> {
        return list.map { mapToUI(single = it) }
    }

    fun mapToUI(single: MovieDetails): MovieDetailsUI {
        return MovieDetailsUI(
            id = single.id,
            isAdult = single.isAdult,
            title = single.title,
            originalTitle = single.originalTitle,
            popularity = single.popularity,
            voteAverage = single.voteAverage,
            voteCount = single.voteCount,
            posterPath = single.posterPath,
            backdropPath = single.backdropPath,
            overview = single.overview,
            releaseDate = single.releaseDate,
            budget = single.budget,
            revenue = single.revenue,
            runtime = single.runtime,
            tagline = single.tagline,
            hasVideo = single.hasVideo,
            genres = GenresModelUIMapper.mapToUI(list = single.genres ?: emptyList())
        )
    }

    fun mapToModel(list: List<MovieDetailsUI>): List<MovieDetails> {
        return list.map { mapToModel(single = it) }
    }

    fun mapToModel(single: MovieDetailsUI): MovieDetails {
        return MovieDetails(
            id = single.id,
            isAdult = single.isAdult,
            title = single.title,
            originalTitle = single.originalTitle,
            popularity = single.popularity,
            voteAverage = single.voteAverage,
            voteCount = single.voteCount,
            posterPath = single.posterPath,
            backdropPath = single.backdropPath,
            overview = single.overview,
            releaseDate = single.releaseDate,
            budget = single.budget,
            revenue = single.revenue,
            runtime = single.runtime,
            tagline = single.tagline,
            hasVideo = single.hasVideo,
            genres = GenresModelUIMapper.mapToModel(list = single.genres),
            genreIds = single.genres.map { it.id }
        )
    }

}