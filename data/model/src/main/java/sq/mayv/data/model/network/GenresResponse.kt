package sq.mayv.data.model.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerializedName("genres") val genres: List<Genre>
)