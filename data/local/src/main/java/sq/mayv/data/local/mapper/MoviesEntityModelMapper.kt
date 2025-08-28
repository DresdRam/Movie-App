package sq.mayv.data.local.mapper

import sq.mayv.core.common.extension.toDateMillis
import sq.mayv.core.common.extension.toDateString
import sq.mayv.data.local.entity.MovieDetailsEntity
import sq.mayv.data.local.entity.relation.MovieWithGenres
import sq.mayv.data.model.network.MovieDetails

object MoviesEntityModelMapper {

    fun mapToModel(list: List<MovieWithGenres>): List<MovieDetails> {
        return list.map { mapToModel(single = it) }
    }

    fun mapToModel(single: MovieWithGenres): MovieDetails {
        return MovieDetails(
            id = single.movie.id,
            isAdult = single.movie.adult,
            title = single.movie.title,
            originalTitle = single.movie.originalTitle,
            popularity = single.movie.popularity,
            voteAverage = single.movie.voteAverage,
            voteCount = single.movie.voteCount,
            posterPath = single.movie.posterPath,
            backdropPath = single.movie.backdropPath,
            overview = single.movie.overview,
            releaseDate = single.movie.releaseDate.toDateString(),
            budget = single.movie.budget,
            revenue = single.movie.revenue,
            runtime = single.movie.runtime,
            status = single.movie.status,
            tagline = single.movie.tagline,
            hasVideo = single.movie.video,
            genres = GenresEntityModelMapper.mapToModel(list = single.genres),
            genreIds = single.genres.map { it.id }
        )
    }

    fun mapToEntity(list: List<MovieDetails>): List<MovieDetailsEntity> {
        return list.map { mapToEntity(single = it) }
    }

    fun mapToEntity(single: MovieDetails): MovieDetailsEntity {
        return MovieDetailsEntity(
            id = single.id,
            adult = single.isAdult,
            title = single.title,
            originalTitle = single.originalTitle,
            popularity = single.popularity,
            voteAverage = single.voteAverage,
            voteCount = single.voteCount,
            posterPath = single.posterPath,
            backdropPath = single.backdropPath,
            overview = single.overview,
            releaseDate = single.releaseDate.toDateMillis(),
            budget = single.budget,
            revenue = single.revenue,
            runtime = single.runtime,
            status = single.status,
            tagline = single.tagline,
            video = single.hasVideo,
        )
    }

}