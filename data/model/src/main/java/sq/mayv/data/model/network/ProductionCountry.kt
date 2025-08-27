package sq.mayv.data.model.network

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1") val isoCode: String,
    @SerializedName("name") val name: String
)