package sq.mayv.data.local.datasource

import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.core.common.Language
import sq.mayv.core.common.Source
import sq.mayv.data.local.dao.MoviesDao
import sq.mayv.data.local.mapper.GenresEntityModelMapper
import sq.mayv.data.local.mapper.MoviesEntityModelMapper
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesMapper: MoviesEntityModelMapper,
    private val genresMapper: GenresEntityModelMapper
) : ILocalDataSource {

    override suspend fun getUpcomingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        return try {
            val movies = moviesDao.getUpcomingMovies()
            GenericState.Success(
                data = moviesMapper.mapToModel(list = movies),
                source = Source.Local
            )
        } catch (e: Exception) {
            GenericState.Failure(errorCode = ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun getTopRatedMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        return try {
            val movies = moviesDao.getTopRatedMovies()
            GenericState.Success(
                data = moviesMapper.mapToModel(list = movies),
                source = Source.Local
            )
        } catch (e: Exception) {
            GenericState.Failure(errorCode = ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun getPopularMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        return try {
            val movies = moviesDao.getPopularMovies()
            GenericState.Success(
                data = moviesMapper.mapToModel(list = movies),
                source = Source.Local
            )
        } catch (e: Exception) {
            GenericState.Failure(errorCode = ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun loadAllGenres(language: Language): GenericState<List<Genre>> {
        return try {
            val genres = moviesDao.getAllGenres()
            GenericState.Success(
                data = genresMapper.mapToModel(list = genres),
                source = Source.Local
            )
        } catch (e: Exception) {
            GenericState.Failure(errorCode = ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun getTrendingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        return try {
            val movies = moviesDao.getTrendingMovies()
            GenericState.Success(
                data = moviesMapper.mapToModel(list = movies),
                source = Source.Local
            )
        } catch (e: Exception) {
            GenericState.Failure(errorCode = ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun getMovieDetails(
        movieId: Int,
        language: Language
    ): GenericState<MovieDetails?> {
        return try {
            val movie = moviesDao.getMovieById(id = movieId)
            movie?.let {
                GenericState.Success(
                    data = moviesMapper.mapToModel(single = movie),
                    source = Source.Local
                )
            } ?: run {
                GenericState.Success(data = null, source = Source.Local)
            }
        } catch (e: Exception) {
            GenericState.Failure(errorCode = ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun insertGenres(list: List<Genre>) {
        val genreEntities = genresMapper.mapToEntity(list = list)
        moviesDao.insertGenres(genres = genreEntities)
    }

    override suspend fun insertMovies(list: List<MovieDetails>) {
        val movieEntities = moviesMapper.mapToEntity(list = list)
        moviesDao.insertMovies(movies = movieEntities)
    }

}