package sq.mayv.core.testing

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sq.mayv.data.model.network.MovieDetails
import sq.mayv.data.model.network.PagingResponse
import java.io.InputStreamReader

object TestingUtils {
    private val gson = Gson()

    inline fun <reified T> Gson.fromJsonGeneric(json: String): T =
        this.fromJson(json, object : TypeToken<T>() {}.type)

    fun moviesJsonAsString(): String {
        val resourceAsStream = javaClass.classLoader?.getResourceAsStream("MoviesResponse.json")
        val reader = InputStreamReader(resourceAsStream)
        return reader.use { it.readText() }
    }

    fun moviesJsonAsItems(): List<MovieDetails> {
        val json = moviesJsonAsString()
        val response: PagingResponse<MovieDetails> = gson.fromJsonGeneric(json)
        return response.results
    }
}