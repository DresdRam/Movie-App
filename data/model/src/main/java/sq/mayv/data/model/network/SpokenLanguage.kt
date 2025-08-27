package sq.mayv.data.model.network

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val isoCode: String,
    @SerializedName("name") val name: String
)