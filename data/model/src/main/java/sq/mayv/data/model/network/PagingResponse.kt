package sq.mayv.data.model.network

import com.google.gson.annotations.SerializedName

class PagingResponse<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("results") val results: List<T>
)