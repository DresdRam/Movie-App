package sq.mayv.data.remote.datasource

import android.util.Log
import com.google.gson.Gson
import sq.mayv.core.common.ErrorCode
import sq.mayv.core.common.GenericState
import sq.mayv.data.model.movies.Language
import sq.mayv.data.model.network.Genre
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.remote.BuildConfig
import sq.mayv.data.remote.network.MoviesApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: MoviesApiService
) : IRemoteDataSource {

    override suspend fun loadUpcomingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        val results = apiService.getUpcomingMovies(page = pageIndex, language = language.code)
        return if (results.isSuccessful) {
            val body = results.body()?.results?.map {
                it.appendImageUrl()
            } ?: emptyList()
            GenericState.Success(data = body)
        } else {
            GenericState.Failure(errorCode = ErrorCode.from(code = results.code()))
        }
    }

    override suspend fun loadTopRatedMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        val results = apiService.getTopRatedMovies(page = pageIndex, language = language.code)
        return if (results.isSuccessful) {
            val body = results.body()?.results?.map {
                it.appendImageUrl()
            } ?: emptyList()
            GenericState.Success(data = body)
        } else {
            GenericState.Failure(errorCode = ErrorCode.from(code = results.code()))
        }
    }

    override suspend fun loadPopularMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        val results = apiService.getPopularMovies(page = pageIndex, language = language.code)
        return if (results.isSuccessful) {
            val body = results.body()?.results?.map {
                it.appendImageUrl()
            } ?: emptyList()
            GenericState.Success(data = body)
        } else {
            GenericState.Failure(errorCode = ErrorCode.from(code = results.code()))
        }
    }

    override suspend fun loadTrendingMovies(
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        val results = apiService.getTrendingMovies(page = pageIndex, language = language.code)
        return if (results.isSuccessful) {
            val body = results.body()?.results?.map {
                it.appendImageUrl()
            } ?: emptyList()
            GenericState.Success(data = body)
        } else {
            GenericState.Failure(errorCode = ErrorCode.from(code = results.code()))
        }
    }

    override suspend fun loadAllGenres(language: Language): GenericState<List<Genre>> {
        val results = apiService.getGenres(language = language.code)
        return if (results.isSuccessful) {
            val body = results.body()?.genres ?: emptyList()
            GenericState.Success(data = body)
        } else {
            GenericState.Failure(errorCode = ErrorCode.from(code = results.code()))
        }
    }

    override suspend fun search(
        query: String,
        pageIndex: Int,
        language: Language
    ): GenericState<List<MovieDetails>> {
        val results = apiService.search(
            query = query,
            page = pageIndex,
            language = language.code
        )
        return if (results.isSuccessful) {
            val body = results.body()?.results?.map {
                it.appendImageUrl()
            } ?: emptyList()
            GenericState.Success(data = body)
        } else {
            GenericState.Failure(errorCode = ErrorCode.from(code = results.code()))
        }
    }

    override suspend fun loadMovieDetails(
        movieId: Int,
        language: Language
    ): GenericState<MovieDetails?> {
        val results = apiService.getMovieDetails(movieId = movieId, language = language.code)
        return if (results.isSuccessful) {
            val body = results.body()?.appendImageUrl()
            GenericState.Success(data = body)
        } else {
            GenericState.Failure(errorCode = ErrorCode.from(code = results.code()))
        }
    }

    private fun MovieDetails.appendImageUrl(): MovieDetails {
        this.posterPath = "${BuildConfig.IMAGE_URL}/w500${this.posterPath}"
        return this
    }
}