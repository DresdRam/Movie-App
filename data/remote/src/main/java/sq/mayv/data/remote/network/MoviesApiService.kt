package sq.mayv.data.remote.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import sq.mayv.data.model.network.GenresResponse
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.model.network.PagingResponse
import sq.mayv.data.remote.BuildConfig

interface MoviesApiService {

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
        private const val GENRES_ENDPOINT = "/genre/movie/list"
        private const val SEARCH_MOVIES_ENDPOINT = "/search/movie"
        private const val MOVIE_DETAILS_ENDPOINT = "/movie/{movieId}"
        private const val UPCOMING_MOVIES_ENDPOINT = "/movie/upcoming"
        private const val POPULAR_MOVIES_ENDPOINT = "/movie/popular"
        private const val TRENDING_MOVIES_ENDPOINT = "/trending/movie/day"
    }

    @GET(GENRES_ENDPOINT)
    suspend fun getGenres(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Query("language") language: String = "en"
    ): GenresResponse

    @GET(SEARCH_MOVIES_ENDPOINT)
    suspend fun search(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String = "en"
    ): PagingResponse<MovieDetails>

    @GET(MOVIE_DETAILS_ENDPOINT)
    suspend fun getMovieDetails(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "en"
    ): MovieDetails

    @GET(UPCOMING_MOVIES_ENDPOINT)
    suspend fun getUpcomingMovies(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Query("page") page: Int,
        @Query("language") language: String = "en"
    ): PagingResponse<MovieDetails>

    @GET(POPULAR_MOVIES_ENDPOINT)
    suspend fun getPopularMovies(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Query("page") page: Int,
        @Query("language") language: String = "en"
    ): PagingResponse<MovieDetails>

    @GET(TRENDING_MOVIES_ENDPOINT)
    suspend fun getTrendingMovies(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Query("page") page: Int,
        @Query("language") language: String = "en"
    ): PagingResponse<MovieDetails>

}